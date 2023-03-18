package com.portfolio.postproject.user.controller;

import com.portfolio.postproject.user.dto.FindUserInfoRequestDto;
import com.portfolio.postproject.user.service.FindUserService;
import com.portfolio.postproject.common.component.ValidationComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class FindUserRestController {

	private final FindUserService findUserService;
	private final ValidationComponent validationComponent;

	//아이디 찾기 위한 이메일 인증키 발송
	@PostMapping("/find-userId-auth-send")
	public ResponseEntity<?> findUserIdAuthSend(@RequestParam String userEmail) {

		findUserService.sendEmailForUserId(userEmail);
		return ResponseEntity.ok().build();
	}

	//아이디 찾기 위한 이메일 인증키 인증
	@PostMapping("/find-userId-auth-check")
	public ResponseEntity<?> findUserIdAuthCheck(
		@RequestBody @Valid FindUserInfoRequestDto.EmailAuthToFindId emailAuthToFindId, Errors error) {

		validationComponent.validation(error);
		findUserService.checkAuthKeyForUserId(emailAuthToFindId);

		return new ResponseEntity<>(emailAuthToFindId.getUserEmailAuthKey(), HttpStatus.OK);
	}

	//비밀번호 찾기 위해 아이디 체크
	@PostMapping("/find-userPwd-checkId")
	public ResponseEntity<?> findUserPwdCheckId(@RequestParam String userId) {

		findUserService.checkIdForUserPwd(userId);
		return new ResponseEntity<>(userId, HttpStatus.OK);
	}

	//비밀번호 찾기 위한 이메일 인증키 발송
	@PostMapping("/find-userPwd-auth-send")
	public ResponseEntity<?> findUserPwdAuthSend(
		@RequestBody @Valid FindUserInfoRequestDto.IdAuthToFindPwd idAuthToFindPwd, Errors error) {

		validationComponent.validation(error);
		findUserService.sendEmailForUserPwd(idAuthToFindPwd);

		return ResponseEntity.ok().build();
	}

	//비밀번호 찾기 위한 이메일 인증키 인증
	@PostMapping("/find-userPwd-auth-check")
	public ResponseEntity<?> findUserPwdAuthCheck(
		@RequestBody @Valid FindUserInfoRequestDto.EmailAuthToFindPwd emailAuthToFindPwd, Errors error) {

		validationComponent.validation(error);
		findUserService.sendNewPwd(emailAuthToFindPwd);

		return ResponseEntity.ok().build();
	}


}
