package com.portfolio.postproject.user.exception;

public class AlreadyExistedUserException extends RuntimeException{
	public AlreadyExistedUserException(String message) {
		super(message);
	}
}
