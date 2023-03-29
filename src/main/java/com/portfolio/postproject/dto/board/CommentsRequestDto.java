package com.portfolio.postproject.dto.board;

import lombok.Getter;
import javax.validation.constraints.NotBlank;

@Getter
public class CommentsRequestDto {

    String commentsId;
    String postId;

    @NotBlank(message = "댓글을 입력하세요.")
    String commentsDetail;
}
