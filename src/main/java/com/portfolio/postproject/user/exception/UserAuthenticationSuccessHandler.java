package com.portfolio.postproject.user.exception;

import com.portfolio.postproject.user.components.OauthIdComponents;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Object principal = authentication.getPrincipal();

        if (principal instanceof DefaultOAuth2User) {
            String userId = (String)((DefaultOAuth2User) principal).getAttributes().get("sub");
            response.sendRedirect("/board/main/" + userId);
        }

        if (principal instanceof User) {
            String userId = ((UserDetails) principal).getUsername();
            response.sendRedirect("/board/main/" + userId);
        }
    }
}
