package com.portfolio.postproject.dto.user;

import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.enums.UserRoles;
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

    public UserSessionDto(DiaryUser diaryUser) {
        this.id = diaryUser.getId();
        this.userPwd = diaryUser.getUserPwd();
        this.userEmail = diaryUser.getUserEmail();
        this.nickname  = diaryUser.getNickname();
        this.userStatus = diaryUser.getUserStatus();
        this.userRoles = diaryUser.getUserRoles();
    }
}
