package com.portfolio.postproject.user.dto;

import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.enums.UserRoles;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSessionDto implements Serializable {

    DiaryUser diaryUser;
    private String id;
    private String userPwd;
    private String userEmail;
    private String nickname;
    private String userStatus;
    private UserRoles userRoles;

    //Entity -> Dto
    public UserSessionDto(DiaryUser diaryUser) {
        this.id = diaryUser.getId();
        this.userPwd = diaryUser.getUserPwd();
        this.userEmail = diaryUser.getUserEmail();
        this.nickname  = diaryUser.getNickname();
        this.userStatus = diaryUser.getUserStatus();
        this.userRoles = diaryUser.getUserRoles(); //세션에는 Role안붙을듯?
    }
}
