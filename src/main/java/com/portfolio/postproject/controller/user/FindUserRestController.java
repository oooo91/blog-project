package com.portfolio.postproject.controller.user;

import com.portfolio.postproject.dto.user.FindUserInfoRequestDto;
import com.portfolio.postproject.service.user.FindUserService;
import com.portfolio.postproject.components.common.ValidationComponent;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/find")
public class FindUserRestController {

	private final FindUserService findUserService;
	private final ValidationComponent validationComponent;

	//아이디 찾기 위한 이메일 인증키 발송
	@PostMapping("/userId-auth-send")
	public ResponseEntity<?> findUserIdAuthSend(@RequestParam String userEmail) {

		findUserService.sendEmailForUserId(userEmail);
		return ResponseEntity.ok().build();
	}

	//아이디 찾기 위한 이메일 인증키 인증
	@PostMapping("/userId-auth-check")
	public ResponseEntity<?> findUserIdAuthCheck(
		@RequestBody @Valid FindUserInfoRequestDto.EmailAuthToFindId emailAuthToFindId, Errors error) {

		validationComponent.validation(error);
		findUserService.checkAuthKeyForUserId(emailAuthToFindId);

		return new ResponseEntity<>(emailAuthToFindId.getUserEmailAuthKey(), HttpStatus.OK);
	}

	//비밀번호 찾기 위해 아이디 체크
	@PostMapping("/userPwd-checkId")
	public ResponseEntity<?> findUserPwdCheckId(@RequestParam String userId) {

		findUserService.checkIdForUserPwd(userId);
		return new ResponseEntity<>(userId, HttpStatus.OK);
	}

	//비밀번호 찾기 위한 이메일 인증키 발송
	@PostMapping("/userPwd-auth-send")
	public ResponseEntity<?> findUserPwdAuthSend(
		@RequestBody @Valid FindUserInfoRequestDto.IdAuthToFindPwd idAuthToFindPwd, Errors error) {

		validationComponent.validation(error);
		findUserService.sendEmailForUserPwd(idAuthToFindPwd);

		return ResponseEntity.ok().build();
	}

	//비밀번호 찾기 위한 이메일 인증키 인증
	@PostMapping("/userPwd-auth-check")
	public ResponseEntity<?> findUserPwdAuthCheck(
		@RequestBody @Valid FindUserInfoRequestDto.EmailAuthToFindPwd emailAuthToFindPwd, Errors error) {

		validationComponent.validation(error);
		findUserService.sendNewPwd(emailAuthToFindPwd);

		return ResponseEntity.ok().build();
	}


}
