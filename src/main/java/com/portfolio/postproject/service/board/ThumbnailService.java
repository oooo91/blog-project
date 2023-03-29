package com.portfolio.postproject.service.board;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.portfolio.postproject.dto.board.ThumbnailRequestDto;
import com.portfolio.postproject.dto.board.ThumbnailResponseDto;
import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.exception.board.PostException;
import com.portfolio.postproject.repository.board.PostRepository;
import java.io.IOException;
import java.util.UUID;
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
	public void updateImage(String postId, MultipartFile multipartFile) throws IOException {

		DiaryPost diaryPost = postRepository.findById(Long.valueOf(postId))
			.orElseThrow(() -> new PostException("존재하지 않는 게시글입니다."));

		if (multipartFile != null) {
			ThumbnailResponseDto thumbnailResponseDto = uploadImage(multipartFile);
			diaryPost.setThumbnail(thumbnailResponseDto.getThumbnail());
			diaryPost.setThumbnailName(thumbnailResponseDto.getThumbnailName());
		}
	}


	@Transactional
	public void deleteImage(ThumbnailRequestDto thumbnailRequestDto) {
		DiaryPost diaryPost = postRepository.findById(Long.valueOf(thumbnailRequestDto.getPostId()))
			.orElseThrow(() -> new PostException("존재하지 않는 게시글입니다."));
		diaryPost.setThumbnail(null);

		amazonS3.deleteObject(new DeleteObjectRequest(bucket, diaryPost.getThumbnailName()));
	}


	public ThumbnailResponseDto uploadImage(MultipartFile multipartFile) throws IOException {

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

		return ThumbnailResponseDto.builder()
			.thumbnail(amazonS3.getUrl(bucket, s3FileName).toString()) //url
			.thumbnailName(s3FileName)
			.build();
	}
}
