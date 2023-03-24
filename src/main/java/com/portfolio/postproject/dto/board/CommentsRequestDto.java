package com.portfolio.postproject.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CommentsRequestDto {

    String commentsId;
    String postId;

    @NotBlank(message = "댓글을 입력하세요.")
    String commentsDetail;
}
