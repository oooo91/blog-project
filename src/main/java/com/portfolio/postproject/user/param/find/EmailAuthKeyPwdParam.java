package com.portfolio.postproject.user.param.find;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmailAuthKeyPwdParam {

    String userId;

    @NotBlank(message = "인증키를 입력하세요")
    String userEmailAuthKey;

    @NotBlank(message = "이메일은 필수 항목입니다")
    @Email(message = "이메일 형식에 맞게 입력하세요.")
    String userEmail;
}
