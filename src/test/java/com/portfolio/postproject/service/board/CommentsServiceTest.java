package com.portfolio.postproject.service.board;

import com.portfolio.postproject.dto.board.CommentsResponseDto;
import com.portfolio.postproject.entity.board.PostComments;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.repository.board.CommentsRepository;
import com.portfolio.postproject.repository.user.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentsServiceTest {

	@Mock
	private CommentsRepository commentsRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private CommentsService commentsService;

	@Test
	@DisplayName("댓글 가져오기")
	void getComments() {
		DiaryUser diaryUser = DiaryUser.builder()
			.id("USER")
			.nickname("USER")
			.build();

		List<PostComments> list = new ArrayList<>();
		list.add(PostComments.builder()
				.id(1L)
				.diaryPost(null)
				.diaryUser(diaryUser)
				.commentDetail("TEXT")
				.createdDate(LocalDateTime.now())
			.build());

		when(commentsRepository.findAllByPostId(anyLong())).thenReturn(list);
		List<CommentsResponseDto> response = commentsService.getComments("1", "testUser");

		assertEquals("TEXT", response.get(0).getCommentsDetail());
		assertThat(list.size()).isEqualTo(1);
	}

	@Test
	@DisplayName("닉네임 불러오기")
	void getUserName() {
		DiaryUser diaryUser = DiaryUser.builder()
			.id("USER")
			.nickname("USER")
			.build();

		when(userRepository.findById(anyString())).thenReturn(Optional.ofNullable(diaryUser));
		assertEquals(commentsService.getUserName(anyString()), "USER");
	}

}