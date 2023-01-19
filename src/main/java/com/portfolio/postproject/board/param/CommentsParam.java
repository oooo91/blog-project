package com.portfolio.postproject.board.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CommentsParam {

    String commentsId; //댓글 아이디
    String postId;
    @NotBlank(message = "댓글을 입력하세요.")
    String commentsDetail; //댓글 내용
}
