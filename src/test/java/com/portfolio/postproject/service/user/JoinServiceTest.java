package com.portfolio.postproject.service.user;

import com.portfolio.postproject.dto.user.EmailAuthResponseDto;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.enums.EmailAuth;
import com.portfolio.postproject.repository.user.UserRepository;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JoinServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private JoinService joinService;

	@Test
	@DisplayName("회원 가입하기")
	void saveUserInfo() {
		DiaryUser diaryUser = DiaryUser.builder()
			.id("ID")
			.emailAuthYn(false)
			.build();

		when(userRepository.findByEmailAuthKey(anyString())).thenReturn(
			Optional.ofNullable(diaryUser));

		EmailAuthResponseDto emailAuthResponseDto = joinService.emailAuth(anyString());
		assertEquals(emailAuthResponseDto.getEmailAuthStatus(), EmailAuth.AUTH_SUCCESS.getEmailAuth());

	}
}