package com.portfolio.postproject.dto.user;

import com.portfolio.postproject.entity.user.DiaryUser;
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
                .userName(diaryUser.getNickname())
                .userEmail(diaryUser.getUserEmail())
                .build();
    }
}
