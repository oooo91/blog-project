package com.portfolio.postproject.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class FindUserInfoRequestDto {

    @Data
    public static class EmailAuthToFindId {

        @NotBlank(message = "이메일은 필수 항목입니다")
        @Email(message = "이메일 형식에 맞게 입력하세요.")
        String userEmail;

        @NotBlank(message = "인증키를 입력하세요")
        String userEmailAuthKey;
    }

    @Data
    public static class EmailAuthToFindPwd {

        String userId;

        @NotBlank(message = "이메일은 필수 항목입니다")
        @Email(message = "이메일 형식에 맞게 입력하세요.")
        String userEmail;

        @NotBlank(message = "인증키를 입력하세요")
        String userEmailAuthKey;

    }

    @Data
    public static class IdAuthToFindPwd {

        @NotBlank(message = "내부적으로 문제가 발생헀습니다. 아이디를 다시 입력하고 진행하세요.")
        String userId;

        @NotBlank(message = "이메일은 필수 항목입니다")
        @Email(message = "이메일 형식에 맞게 입력하세요.")
        String userEmail;

    }
}
