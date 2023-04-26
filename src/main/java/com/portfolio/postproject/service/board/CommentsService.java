package com.portfolio.postproject.service.board;

import com.portfolio.postproject.dto.board.CommentsResponseDto;
import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.entity.board.PostComments;
import com.portfolio.postproject.exception.board.CommentsException;
import com.portfolio.postproject.dto.board.CommentsRequestDto;
import com.portfolio.postproject.repository.board.PostRepository;
import com.portfolio.postproject.repository.board.CommentsRepository;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.exception.common.NotFoundUserException;
import com.portfolio.postproject.repository.user.UserRepository;
import javax.transaction.Transactional;
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
	public List<CommentsResponseDto> getComments(String postId, String username) {
		List<PostComments> list = commentsRepository.findAllByPostId(
			Long.parseLong(postId));

		if (list.isEmpty()) {
			return new ArrayList<>();
		}
		return CommentsResponseDto.of(list, username);
	}

	//내 닉네임 가져오기
	public String getUserName(String username) {
		DiaryUser user = userRepository.findById(username)
			.orElseThrow(() -> new NotFoundUserException("아이디가 존재하지 않습니다."));
		return user.getNickname();
	}

	//수정
	@Transactional
	public void updateComments(CommentsRequestDto commentsRequestDto, String username) {
		PostComments postComments = commentsRepository.findById(
				Long.parseLong(commentsRequestDto.getCommentsId()))
			.orElseThrow(() -> new CommentsException("이미 삭제된 댓글입니다."));

		if (!postComments.getDiaryUser().getId().equals(username)) {
			throw new CommentsException("본인이 작성한 댓글만 수정 가능합니다.");
		}
		postComments.setCommentDetail(commentsRequestDto.getCommentsDetail());
	}

	//삭제
	@Transactional
	public void deleteComments(String commentsId, String username) {
		PostComments postComments = commentsRepository.findById(Long.parseLong(commentsId))
			.orElseThrow(() -> new CommentsException("이미 삭제된 댓글입니다."));

		if (!postComments.getDiaryUser().getId().equals(username)) {
			throw new CommentsException("본인이 작성한 댓글만 삭제 가능합니다.");
		}
		commentsRepository.delete(postComments);
	}

	//저장
	public void writeComments(CommentsRequestDto commentsRequestDto, String username) {
		DiaryUser diaryUser = userRepository.findById(username)
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
