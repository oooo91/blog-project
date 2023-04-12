package com.portfolio.postproject.dto.board;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostRequestDto {

    String postId;
    String paramId;
    String postDate;

    @NotBlank(message = "제목을 입력하세요.")
    String postTitle;

    @NotBlank(message = "내용을 작성하세요.")
    String postContent;

}
