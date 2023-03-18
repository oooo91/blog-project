package com.portfolio.postproject.user.dto;

import com.portfolio.postproject.user.entity.DiaryUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MyPageResponseDto {

    private String userName;
    private String userEmail;

    public static MyPageResponseDto of(DiaryUser diaryUser) {
        return MyPageResponseDto.builder()
                .userName(diaryUser.getUserName())
                .userEmail(diaryUser.getUserEmail())
                .build();
    }
}
