package com.portfolio.postproject.components.user;

import org.springframework.stereotype.Component;

@Component
public class OauthIdComponents {

    public static String getOauthId(String email) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) != '@') {
                sb.append(email.charAt(i));
            } else {
                break;
            }
        }
        return sb.toString();
    }
}
