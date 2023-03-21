package com.portfolio.postproject.user.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationFailureHandler.class.getName());

    //AuthenticationFailureHandler 구현한 SimpleUrlAuthenticationFailureHandler -> setDefaultFailureUrl()
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String message = "";

        /*
        로그인 구현 중 문제
        가입하지 않은 계정으로 로그인 시도 시, UsernameNotFoundException이 아닌, BadCredentiasException이 발생했다.
        그 이유는 UserDetailsService에서 발생한 예외는 AbstractUserDetailsauthenticationProvider로 던져지는데,
        AbstractUserDetailsauthenticationProvider의 hideUserNotFoundException이 BadCredentialsException을 내보낸다.

        AuthenticationProvide룰 커스터마이징하면 에러 처리를 분류할 수 있는데,
        DaoAuthenticationProvider에서 hideUserNotFoundExceptions 설정을 디폴트인 true가 아닌 false가 되면 가능하다.
        UserNotFoundException이 발생하더라도 이를 숨기는 hideUserNotFoundExceptions이 true로 되어있으면
        UserNotFoundException을 catch해서 대신 BadCredentialsExeption을 보내주고 있기 떄문이다.
        */

        if (exception instanceof BadCredentialsException) {
            message = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof UsernameNotFoundException) {
            message = exception.getMessage();
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            message = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            message = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        }

        setUseForward(true);
        setDefaultFailureUrl("/user/login?error=true"); //post
        request.setAttribute("errorMessage", message);
        logger.info("예외 메시지: " + exception.getMessage());

        super.onAuthenticationFailure(request, response, exception);
    }
}
