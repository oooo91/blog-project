package com.portfolio.postproject.dto.board;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import lombok.Setter;

@Builder
@Data
public class PostRequestDto {

    String postId;
    String paramId;
    String postDate;

    @NotBlank(message = "제목을 입력하세요.")
    String postTitle;

    @NotBlank(message = "내용을 작성하세요.")
    String postContent;

}
