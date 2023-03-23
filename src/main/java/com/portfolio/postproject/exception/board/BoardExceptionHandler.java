package com.portfolio.postproject.exception.board;

import com.portfolio.postproject.controller.board.CommentsRestController;
import com.portfolio.postproject.controller.board.WriteRestController;
import com.portfolio.postproject.exception.common.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {CommentsRestController.class, WriteRestController.class})
public class BoardExceptionHandler {

	@ExceptionHandler(CommentsException.class)
	public ResponseEntity<ErrorMessage> commentsException(CommentsException exception) {

		return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(PostException.class)
	public ResponseEntity<ErrorMessage> postException(PostException exception) {

		return ResponseEntity.badRequest().body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}
}
