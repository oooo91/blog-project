package com.portfolio.postproject.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class JoinRequestDto {

    @Size(min = 5, message = "아이디는 최소 5자 이상 입력해야 합니다.")
    @NotBlank(message = "아이디는 필수 항목입니다")
    String userId;

    @NotBlank(message = "이름은 필수 항목입니다")
    String userName;

    @Size(min = 8, message = "비밀번호는 최소 8자 이상 입력해야 합니다.")
    @NotBlank(message = "비밀번호는 필수 항목입니다")
    String userPwd;

    @Email(message = "이메일 형식에 맞게 입력하세요.")
    @NotBlank(message = "이메일은 필수 항목입니다")
    String userEmail;


}
