package com.portfolio.postproject.exception.user;

public class AlreadyExistedUserException extends RuntimeException{
	public AlreadyExistedUserException(String message) {
		super(message);
	}
}
