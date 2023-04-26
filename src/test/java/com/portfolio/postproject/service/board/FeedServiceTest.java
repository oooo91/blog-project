package com.portfolio.postproject.service.board;

import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.enums.UserRoles;
import com.portfolio.postproject.repository.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private FeedService feedService;

	@Test
	@DisplayName("관리자 검증")
	void checkAdmin() {
		DiaryUser diaryUser = DiaryUser.builder()
			.id("USER")
			.nickname("USER")
			.userRoles(UserRoles.ADMIN)
			.build();
		when(userRepository.findById(anyString())).thenReturn(Optional.ofNullable(diaryUser));
		assertThat(feedService.checkAdmin("USER")).isTrue();
	}

}