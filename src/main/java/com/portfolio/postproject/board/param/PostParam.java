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
public class PostParam {

    String postId;

    String paramId;

    @NotBlank(message = "제목을 입력하세요.")
    String postTitle;

    @NotBlank(message = "내용을 작성하세요.")
    String postContent;

    String postDate;

}
