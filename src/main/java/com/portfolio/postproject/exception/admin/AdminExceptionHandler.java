package com.portfolio.postproject.exception.admin;

import com.portfolio.postproject.controller.admin.AdminController;
import com.portfolio.postproject.controller.admin.AdminRestController;
import com.portfolio.postproject.exception.common.ErrorMessage;
import com.portfolio.postproject.exception.common.NotFoundUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = {AdminController.class, AdminRestController.class})
public class AdminExceptionHandler {
	@ExceptionHandler(NotFoundUserException.class)
	public ResponseEntity<ErrorMessage> notFoundUserException(NotFoundUserException exception) {

		return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}
}
