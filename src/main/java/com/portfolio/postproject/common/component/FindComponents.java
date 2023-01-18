package com.portfolio.postproject.common.component;

import org.springframework.stereotype.Component;

@Component
public class FindComponents {

    public String getChangeId(String userId) {
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<userId.length(); i++) {
            if(i < userId.length()/2) {
                sb.append(userId.charAt(i));
            } else {
                sb.append("*");
            }
        }
        return sb.toString();
    }
}
