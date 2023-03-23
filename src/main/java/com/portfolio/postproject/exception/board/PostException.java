package com.portfolio.postproject.exception.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostException extends RuntimeException {
    public PostException(String message) {
        super(message);
    }
}
