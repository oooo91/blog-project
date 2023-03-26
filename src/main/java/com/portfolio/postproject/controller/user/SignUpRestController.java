package com.portfolio.postproject.controller.user;

import com.portfolio.postproject.components.common.ValidationComponent;
import com.portfolio.postproject.dto.user.JoinRequestDto;
import com.portfolio.postproject.service.user.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SignUpRestController {

	private final JoinService joinService;
    private final ValidationComponent validationComponent;

	@PostMapping("/user/signup")
	public ResponseEntity<?> userJoinTry(@RequestBody @Valid JoinRequestDto joinRequestDto,
		Errors error) {

        validationComponent.validation(error);
		joinService.saveUserInfo(joinRequestDto);
		return ResponseEntity.ok().build();
	}

}
