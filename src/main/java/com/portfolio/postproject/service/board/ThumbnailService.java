package com.portfolio.postproject.service.board;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.portfolio.postproject.dto.board.ImageResponseDto;
import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.exception.board.PostException;
import com.portfolio.postproject.exception.common.NotFoundUserException;
import com.portfolio.postproject.repository.board.PostRepository;
import com.portfolio.postproject.repository.user.UserRepository;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ThumbnailService {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private final AmazonS3 amazonS3;
	private final PostRepository postRepository;


	@Transactional
	public ImageResponseDto uploadImage(String postId, MultipartFile multipartFile)
		throws IOException {

		if (multipartFile == null) {
			return ImageResponseDto.builder()
				.fileName(null)
				.url(null)
				.build();
		}

		String fileName = multipartFile.getOriginalFilename();
		String s3FileName = UUID.randomUUID() + "-" + fileName;

		String ext = fileName.split("\\.")[1];
		String contentType = "";

		switch (ext) {
			case "jpeg":
				contentType = "image/jpeg";
				break;
			case "png":
				contentType = "image/png";
				break;
			case "txt":
				contentType = "text/plain";
				break;
			case "csv":
				contentType = "text/csv";
				break;
		}

		ObjectMetadata objMeta = new ObjectMetadata();
		objMeta.setContentType(contentType);
		objMeta.setContentLength(multipartFile.getSize());

		amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
		multipartFile.getInputStream().close();

		String url = amazonS3.getUrl(bucket, s3FileName).toString();
		DiaryPost diaryPost = postRepository.findById(Long.valueOf(postId))
			.orElseThrow(() -> new PostException("존재하지 않는 게시글입니다."));
		diaryPost.setThumbnail(url);

		return ImageResponseDto.builder()
			.fileName(s3FileName)
			.url(url)
			.build();
	}

	@Transactional
	public void deleteImage(String postId, String s3FileName) {
		DiaryPost diaryPost = postRepository.findById(Long.valueOf(postId))
			.orElseThrow(() -> new PostException("존재하지 않는 게시글입니다."));
		diaryPost.setThumbnail(null);

		amazonS3.deleteObject(new DeleteObjectRequest(bucket, s3FileName));
	}

}
