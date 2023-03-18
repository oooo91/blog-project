package com.portfolio.postproject.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostException extends RuntimeException {
    public PostException(String message) {
        super(message);
    }
}
