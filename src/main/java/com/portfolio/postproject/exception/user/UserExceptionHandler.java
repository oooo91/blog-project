package com.portfolio.postproject.exception.user;

import com.portfolio.postproject.exception.common.ErrorMessage;
import com.portfolio.postproject.controller.user.FindUserRestController;
import com.portfolio.postproject.controller.user.SignUpRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {FindUserRestController.class, SignUpRestController.class})
public class UserExceptionHandler {

	@ExceptionHandler(NotFoundUserException.class)
	public ResponseEntity<ErrorMessage> failedSignUpException(NotFoundUserException exception) {

		return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(EmailException.class)
	public ResponseEntity<ErrorMessage> failedSignUpException(EmailException exception) {

		return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(AlreadyExistedUserException.class)
	public ResponseEntity<ErrorMessage> alreadyExistedUserException(AlreadyExistedUserException exception) {

		return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(InvalidDtoException.class)
	public ResponseEntity<ErrorMessage> invalidDtoException(InvalidDtoException exception) {

		return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}
}
