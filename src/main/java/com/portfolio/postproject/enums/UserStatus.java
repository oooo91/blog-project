package com.portfolio.postproject.enums;

import lombok.Getter;

@Getter
public enum UserStatus {

    STATUS_READY("READY"),
    STATUS_ACTIVE("ACTIVE"),
    STATUS_WITHDRAW("WITHDRAW");

    private String userStatus;

    UserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
