package com.portfolio.postproject.user.enums;


import lombok.Getter;

@Getter
public enum UserRoles {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    SOCIAL("ROLE_SOCIAL");

    private String userRole;

    UserRoles(String userRole) {
        this.userRole = userRole;
    }
}
