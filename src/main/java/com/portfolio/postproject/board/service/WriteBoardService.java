package com.portfolio.postproject.board.service;

import com.portfolio.postproject.board.dto.BoardResponseDto;
import com.portfolio.postproject.board.entity.DiaryPost;
import com.portfolio.postproject.board.exception.PostException;
import com.portfolio.postproject.board.dto.PostReqeustDto;
import com.portfolio.postproject.board.repository.PostRepository;
import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.repository.UserRepository;
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
	public void updateBoard(PostReqeustDto postReqeustDto) {
		DiaryPost diaryPost = postRepository.findById(Long.parseLong(postReqeustDto.getPostId()))
			.orElseThrow(() -> new PostException("게시글을 저장할 수 없습니다."));

		diaryPost.setPostDate(LocalDate.parse(postReqeustDto.getPostDate(),
			DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));

		diaryPost.setPostTitle(postReqeustDto.getPostTitle());
		diaryPost.setPostContent(postReqeustDto.getPostContent());
	}

	//삭제
	public void deleteBoard(HttpServletRequest request) {
		DiaryPost diaryPost = postRepository.findById(
				Long.parseLong(request.getParameter("postId")))
			.orElseThrow(() -> new PostException("존재하지 않는 게시글입니다."));
		postRepository.delete(diaryPost);
	}

	//저장
	public long saveBoard(PostReqeustDto postReqeustDto) {
		DiaryUser diaryUser = userRepository.findById(postReqeustDto.getParamId())
			.orElseThrow(() -> new PostException("작성자가 존재하지 않습니다.")); //작성자 찾기

		DiaryPost diaryPost = DiaryPost.builder()
			.diaryUser(diaryUser)
			.postDate(LocalDate.parse(postReqeustDto.getPostDate(), DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
			.postContent(postReqeustDto.getPostContent())
			.postTitle(postReqeustDto.getPostTitle())
			.build();

		return postRepository.save(diaryPost).getId(); //postId값 추출
	}
}
