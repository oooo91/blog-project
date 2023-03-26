package com.portfolio.postproject.entity.user;

import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.enums.UserRoles;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
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

    private String nickname;
    private String userEmail;
    private String userPwd;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime loginAt;

    private String userStatus;
    private String socialType;

    @Enumerated(EnumType.STRING)
    private UserRoles userRoles;

    @Builder.Default
    @OneToMany(mappedBy = "diaryUser")
    private List<DiaryPost> diaryPost = new ArrayList<>();

    private String emailAuthKey;
    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;

    private String findIdEmailAuthKey;
    private boolean findIdEmailAuthYn;
    private LocalDateTime findIdLimitDt; //아이디 인증키 유효기간

    private String findPwdEmailAuthKey;
    private boolean findPwdEmailAuthYn;
    private LocalDateTime findPwdLimitDt; //비밀번호 인증키 유효기간
}
