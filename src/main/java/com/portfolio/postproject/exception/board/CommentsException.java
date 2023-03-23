package com.portfolio.postproject.exception.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentsException extends RuntimeException {
    public CommentsException(String message) {
        super(message);
    }
}
