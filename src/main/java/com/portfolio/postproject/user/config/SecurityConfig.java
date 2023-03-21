package com.portfolio.postproject.user.config;

import com.portfolio.postproject.user.exception.UserAuthenticationFailureHandler;
import com.portfolio.postproject.user.exception.UserAuthenticationSuccessHandler;
import com.portfolio.postproject.user.enums.UserRoles;
import com.portfolio.postproject.user.service.LoginService;
import com.portfolio.postproject.user.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크
public class SecurityConfig {

    private final LoginService loginService;
    private final OAuthService oAuthService;

    @Bean
	public UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Bean
	public UserAuthenticationSuccessHandler getSuccessHandler() {
        return new UserAuthenticationSuccessHandler();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() { //비번 확인
        DaoAuthenticationProvider bean = new DaoAuthenticationProvider();
        bean.setHideUserNotFoundExceptions(false);
        bean.setUserDetailsService(loginService);
        return bean;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //staticResource ignore
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable(); //CSRF protection ignore
        http.headers().frameOptions().sameOrigin(); //header ignore

        http.authorizeRequests()
                .antMatchers("/user/signup",
                                        "/user/login",
                                        "/user/find-password")
                                        .permitAll();

        http.authorizeRequests()
                .antMatchers("/admin/**")
                .hasAnyAuthority(UserRoles.ADMIN.getUserRole());

        http.formLogin()
                .loginPage("/user/login")
                .successHandler(getSuccessHandler())
                .failureHandler(getFailureHandler())
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/login")
                .invalidateHttpSession(true); //세션 초기화

        http.oauth2Login() //OAuth2 로그인 설정 시작점
                .loginPage("/user/login")
                .failureUrl("/user/login")
                .userInfoEndpoint() //OAuth2 로그인 서공 후 사용자 정보 가져온다.
                .userService(oAuthService); //사용자 정보 처리할 때 사용하는 서비스.

        http.sessionManagement()
                .maximumSessions(2)
                .maxSessionsPreventsLogin(false) //true인 경우 현재 요청하는 사용자의 인증 실패, false인 경우 기존 사용자의 세션 만료
                .expiredUrl("/user/login");

        return http.build();
    }



}
