package com.portfolio.postproject.board.service;

import com.portfolio.postproject.board.dto.CommentsResponseDto;
import com.portfolio.postproject.board.entity.DiaryPost;
import com.portfolio.postproject.board.entity.PostComments;
import com.portfolio.postproject.board.exception.CommentsException;
import com.portfolio.postproject.board.dto.CommentsRequestDto;
import com.portfolio.postproject.board.repository.PostRepository;
import com.portfolio.postproject.board.repository.CommentsRepository;
import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.exception.NotFoundUserException;
import com.portfolio.postproject.user.repository.UserRepository;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentsService {

	private final PostRepository postRepository;
	private final CommentsRepository commentsRepository;
	private final UserRepository userRepository;

	//댓글 가져오기
	public List<CommentsResponseDto> getComments(HttpServletRequest request, Principal principal) {
		List<PostComments> list = commentsRepository.findAllByPostId(
			Long.parseLong(request.getParameter("postId")));

		if (list.isEmpty()) {
			return new ArrayList<>();
		}
		return CommentsResponseDto.of(list, principal.getName());
	}

	//내 닉네임 가져오기
	public String getUserName(Principal principal) {
		DiaryUser user = userRepository.findById(principal.getName())
			.orElseThrow(() -> new NotFoundUserException("아이디가 존재하지 않습니다."));
		return user.getUserName();
	}

	//수정
	public void updateComments(CommentsRequestDto commentsRequestDto, Principal principal) {
		PostComments postComments = commentsRepository.findById(
				Long.parseLong(commentsRequestDto.getCommentsId()))
			.orElseThrow(() -> new CommentsException("이미 삭제된 댓글입니다."));

		if (!postComments.getDiaryUser().getId().equals(principal.getName())) {
			throw new CommentsException("본인이 작성한 댓글만 수정 가능합니다.");
		}
		postComments.setCommentDetail(commentsRequestDto.getCommentsDetail());
	}

	//삭제
	public void deleteComments(String commentsId, Principal principal) {
		PostComments postComments = commentsRepository.findById(Long.parseLong(commentsId))
			.orElseThrow(() -> new CommentsException("이미 삭제된 댓글입니다."));

		if (!postComments.getDiaryUser().getId().equals(principal.getName())) {
			throw new CommentsException("본인이 작성한 댓글만 삭제 가능합니다.");
		}
		commentsRepository.delete(postComments);
	}

	//저장
	public void writeComments(CommentsRequestDto commentsRequestDto, Principal principal) {
		DiaryUser diaryUser = userRepository.findById(principal.getName())
			.orElseThrow(() -> new CommentsException("존재하지 않는 회원입니다."));

		DiaryPost diaryPost = postRepository.findById(
				Long.parseLong(commentsRequestDto.getPostId()))
			.orElseThrow(() -> new CommentsException("이미 삭제된 게시글입니다."));

		PostComments postComments = PostComments.builder()
			.commentDetail(commentsRequestDto.getCommentsDetail())
			.createdDate(LocalDateTime.now())
			.diaryPost(diaryPost)
			.diaryUser(diaryUser)
			.build();
		commentsRepository.save(postComments);
	}
}
