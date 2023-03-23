package com.portfolio.postproject.handler;

import com.portfolio.postproject.dto.user.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

        if (authentication instanceof OAuth2AuthenticationToken) {
            String userId = ((DefaultOAuth2User) authentication.getPrincipal()).getName();
            response.sendRedirect("/board/main/" + userId);
        }

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            String userId = ((CustomUserDetails) authentication.getPrincipal()).getName();
            response.sendRedirect("/board/main/" + userId);
        }
    }
}
