package com.portfolio.postproject.dto.user;

import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MyPageDetailRequestDto {

	private String paramId;

	@NotBlank(message = "이름은 필수 항목입니다")
	private String userName;
}
