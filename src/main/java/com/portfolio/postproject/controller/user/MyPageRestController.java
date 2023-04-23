package com.portfolio.postproject.controller.user;

import com.portfolio.postproject.components.common.ValidationComponent;
import com.portfolio.postproject.dto.user.MyPageDetailPwdRequestDto;
import com.portfolio.postproject.dto.user.MyPageDetailRequestDto;
import com.portfolio.postproject.service.user.MyPageService;
import java.io.IOException;
import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MyPageRestController {

	private final ValidationComponent validationComponent;
	private final MyPageService myPageService;

	//이름, 비밀번호, 프로필 사진 수정
	@PreAuthorize("isAuthenticated() and (#myPageDetailRequestDto.paramId == principal.name)")
	@PutMapping("/myPage/update")
	public ResponseEntity<?> update(
		@Valid @RequestPart(value = "data") MyPageDetailRequestDto myPageDetailRequestDto,
		@RequestPart(value = "img", required = false) MultipartFile multipartFile,
		Principal principal, Errors error) throws IOException {

		validationComponent.validation(error);
		myPageService.myPageDetailUpdate(myPageDetailRequestDto, multipartFile);
		return ResponseEntity.ok().build();
	}

	//이름, 비밀번호, 프로필 사진 수정
	@PreAuthorize("isAuthenticated() and (#myPageDetailPwdRequestDto.paramId == principal.name)")
	@PutMapping("/myPage/pwdUpdate")
	public ResponseEntity<?> pwdUpdate(
		@Valid @RequestBody MyPageDetailPwdRequestDto myPageDetailPwdRequestDto,
		Principal principal, Errors error) throws IOException {

		validationComponent.validation(error);
		myPageService.myPageDetailPwdUpdate(myPageDetailPwdRequestDto);
		return ResponseEntity.ok().build();
	}

	//탈퇴
	@PreAuthorize("isAuthenticated() and (#paramId == principal.name)")
	@PostMapping("/withdrawal/{paramId}")
	public ResponseEntity<?> withdrawal(@PathVariable("paramId") String paramId, Principal principal) {

		myPageService.withdrawal(paramId);
		return ResponseEntity.ok().build();
	}
}
