package com.portfolio.postproject.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommentsRequestDto {

    String commentsId;
    String postId;

    @NotBlank(message = "댓글을 입력하세요.")
    String commentsDetail;
}
