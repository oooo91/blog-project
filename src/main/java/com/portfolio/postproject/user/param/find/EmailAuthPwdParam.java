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
public class EmailAuthPwdParam {

    @NotBlank(message = "이메일은 필수 항목입니다")
    @Email(message = "이메일 형식에 맞게 입력하세요.")
    String userEmail;

    @NotBlank(message = "내부적으로 문제가 발생헀습니다. 아이디를 다시 입력하고 진행하세요.")
    String userId;
}
