package com.portfolio.postproject.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class DiaryUser implements DiaryUserCode {

    @Id
    private String userId;

    @NotBlank
    private String userName;

    @NotBlank
    private String userEmail;

    @NotBlank
    private String userPwd;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

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
