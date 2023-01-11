package com.portfolio.postproject.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class DiaryUser {

    @Id
    @Column(name = "userId")
    private String Id;

    @NotBlank
    private String userName;

    @NotBlank
    private String userEmail;

    private String userPwd;

    @NotNull
    private LocalDateTime createdAt; //mappedSuperclass - AssociateOverride 로 변경하기

    private LocalDateTime updatedAt;

    private LocalDateTime loginAt; //로그인 날짜

    @NotNull
    private boolean level;

    private String emailAuthKey;
    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;

    private String newPasswordKey;
    private LocalDateTime newPasswordLimitDt;

    @NotBlank
    private String userStatus;

}
