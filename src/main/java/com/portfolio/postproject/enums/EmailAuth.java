package com.portfolio.postproject.enums;

import lombok.Getter;

@Getter
public enum EmailAuth {

    AUTH_SUCCESS("SUCCESS"),
    AUTH_FAIL("FAIL"),
    AUTH_DONE("DONE");

    private String emailAuth;

    EmailAuth(String emailAuth) {
        this.emailAuth = emailAuth;
    }
}
