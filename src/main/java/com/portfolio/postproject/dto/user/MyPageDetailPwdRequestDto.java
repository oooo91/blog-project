package com.portfolio.postproject.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MyPageDetailPwdRequestDto {

	private String paramId;

	@Size(min = 8, message = "비밀번호는 최소 8자 이상 입력해야 합니다.")
	@NotBlank(message = "비밀번호는 필수 항목입니다")
	private String userPwd;

	@Size(min = 8, message = "비밀번호는 최소 8자 이상 입력해야 합니다.")
	@NotBlank(message = "비밀번호는 필수 항목입니다")
	private String userNewPwd;
}
