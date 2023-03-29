package com.portfolio.postproject.service.board;

import com.portfolio.postproject.dto.board.BoardResponseDto;
import com.portfolio.postproject.dto.board.ThumbnailResponseDto;
import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.exception.board.PostException;
import com.portfolio.postproject.dto.board.PostRequestDto;
import com.portfolio.postproject.repository.board.PostRepository;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.repository.board.WeatherRepository;
import com.portfolio.postproject.repository.user.UserRepository;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class WriteBoardService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final WeatherRepository weatherRepository;
	private final ThumbnailService thumbnailService;

	//디테일 페이지
	public BoardResponseDto getDetail(HttpServletRequest request) {
		DiaryPost diaryPost = postRepository.findById(
				Long.parseLong(request.getParameter("postId"))).
			orElseThrow(() -> new PostException("이미 삭제된 게시글입니다."));

		return BoardResponseDto.of(diaryPost);
	}

	//수정
	@Transactional
	public void updateBoard(PostRequestDto postRequestDto) {
		DiaryPost diaryPost = postRepository.findById(Long.parseLong(postRequestDto.getPostId()))
			.orElseThrow(() -> new PostException("게시글을 저장할 수 없습니다."));
		LocalDate date = LocalDate.parse(postRequestDto.getPostDate(), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

		if (weatherRepository.existsById(date)) {
			diaryPost.setIcon(weatherRepository.findIconByDate(date));
		} else {
			diaryPost.setIcon(null);
		}
		diaryPost.setPostDate(date);
		diaryPost.setPostTitle(postRequestDto.getPostTitle());
		diaryPost.setPostContent(postRequestDto.getPostContent());
	}

	//삭제
	@Transactional
	public void deleteBoard(HttpServletRequest request) {
		DiaryPost diaryPost = postRepository.findById(
				Long.parseLong(request.getParameter("postId")))
			.orElseThrow(() -> new PostException("존재하지 않는 게시글입니다."));
		postRepository.delete(diaryPost);
	}

	//저장
	public long saveBoard(PostRequestDto postRequestDto, MultipartFile multipartFile)
		throws IOException {
		DiaryUser diaryUser = userRepository.findById(postRequestDto.getParamId())
			.orElseThrow(() -> new PostException("작성자가 존재하지 않습니다."));
		LocalDate date = LocalDate.parse(postRequestDto.getPostDate(), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));

		DiaryPost diaryPost = DiaryPost.builder()
			.diaryUser(diaryUser)
			.postDate(date)
			.postContent(postRequestDto.getPostContent())
			.postTitle(postRequestDto.getPostTitle())
			.build();

		if (weatherRepository.existsById(date)) {
			diaryPost.setIcon(weatherRepository.findIconByDate(date));
		}

		if (multipartFile != null) {
			ThumbnailResponseDto thumbnailResponseDto = thumbnailService.uploadImage(multipartFile);
			diaryPost.setThumbnail(thumbnailResponseDto.getThumbnail());
			diaryPost.setThumbnailName(thumbnailResponseDto.getThumbnailName());
		}

		return postRepository.save(diaryPost).getId();
	}
}
