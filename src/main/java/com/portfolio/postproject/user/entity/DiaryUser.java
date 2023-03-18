package com.portfolio.postproject.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class DiaryUser {

    @Id
    @Column(name = "userId")
    private String id;

    private String userName;
    private String userEmail;
    private String userPwd;

    private LocalDateTime createdAt; //mappedSuperclass - AssociateOverride 로 변경하기
    private LocalDateTime updatedAt;

    private LocalDateTime loginAt;

    private String emailAuthKey;
    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;

    private String userStatus;
    private boolean level;

    //아이디, 비밀번호 찾기 인증키 추가
    private String findIdEmailAuthKey;
    private boolean findIdEmailAuthYn;
    private LocalDateTime findIdLimitDt; //아이디 인증키 유효기간

    private String findPwdEmailAuthKey;
    private boolean findPwdEmailAuthYn;
    private LocalDateTime findPwdLimitDt; //비밀번호 인증키 유효기간
}
