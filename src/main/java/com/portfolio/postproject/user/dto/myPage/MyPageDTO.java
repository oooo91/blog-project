package com.portfolio.postproject.user.dto.myPage;

import com.portfolio.postproject.user.entity.DiaryUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MyPageDTO {

    private String userName;
    private String userEmail;

    public static MyPageDTO of(DiaryUser diaryUser) {
        return MyPageDTO.builder()
                .userName(diaryUser.getUserName())
                .userEmail(diaryUser.getUserEmail())
                .build();
    }
}
