package com.portfolio.postproject.service.user;

import com.portfolio.postproject.dto.user.CustomUserDetails;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.enums.UserStatus;
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
class LoginServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private LoginService loginService;

	@Test
	@DisplayName("로그인")
	void login() {
		DiaryUser diaryUser = DiaryUser.builder()
			.id("NAME")
			.userStatus(UserStatus.STATUS_ACTIVE.getUserStatus())
			.build();
		when(userRepository.findById(anyString())).thenReturn(Optional.ofNullable(diaryUser));

		CustomUserDetails customUserDetails = (CustomUserDetails) loginService.loadUserByUsername(anyString());
		assertThat(customUserDetails.getUsername()).isEqualTo("NAME");
	}

}