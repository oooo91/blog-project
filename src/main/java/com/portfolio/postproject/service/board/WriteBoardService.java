package com.portfolio.postproject.service.board;

import com.portfolio.postproject.dto.board.BoardResponseDto;
import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.exception.board.PostException;
import com.portfolio.postproject.dto.board.PostRequestDto;
import com.portfolio.postproject.repository.board.PostRepository;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.repository.user.UserRepository;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class WriteBoardService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	//디테일 페이지
	public BoardResponseDto getDetail(HttpServletRequest request) {
		DiaryPost diaryPost = postRepository.findById(
				Long.parseLong(request.getParameter("postId"))).
			orElseThrow(() -> new PostException("이미 삭제된 게시글입니다."));

		return BoardResponseDto.of(diaryPost);
	}

	//수정
	public void updateBoard(PostRequestDto postRequestDto) {
		DiaryPost diaryPost = postRepository.findById(Long.parseLong(postRequestDto.getPostId()))
			.orElseThrow(() -> new PostException("게시글을 저장할 수 없습니다."));

		diaryPost.setPostDate(LocalDate.parse(postRequestDto.getPostDate(),
			DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));

		diaryPost.setPostTitle(postRequestDto.getPostTitle());
		diaryPost.setPostContent(postRequestDto.getPostContent());
	}

	//삭제
	public void deleteBoard(HttpServletRequest request) {
		DiaryPost diaryPost = postRepository.findById(
				Long.parseLong(request.getParameter("postId")))
			.orElseThrow(() -> new PostException("존재하지 않는 게시글입니다."));
		postRepository.delete(diaryPost);
	}

	//저장
	public long saveBoard(PostRequestDto postRequestDto) {
		DiaryUser diaryUser = userRepository.findById(postRequestDto.getParamId())
			.orElseThrow(() -> new PostException("작성자가 존재하지 않습니다.")); //작성자 찾기

		DiaryPost diaryPost = DiaryPost.builder()
			.diaryUser(diaryUser)
			.postDate(LocalDate.parse(postRequestDto.getPostDate(), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
			.postContent(postRequestDto.getPostContent())
			.postTitle(postRequestDto.getPostTitle())
			.build();

		return postRepository.save(diaryPost).getId(); //postId값 추출
	}
}
