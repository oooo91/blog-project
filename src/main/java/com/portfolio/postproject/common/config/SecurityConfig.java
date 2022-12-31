package com.portfolio.postproject.common.config;

import com.portfolio.postproject.user.enums.UserRole;
import com.portfolio.postproject.user.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginService loginService;

    //예외 핸들러
    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Bean
    UserAuthenticationSuccessHandler getSuccessHandler() {
        return new UserAuthenticationSuccessHandler();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider bean = new DaoAuthenticationProvider();
        bean.setHideUserNotFoundExceptions(false);
        bean.setUserDetailsService(loginService);

        return bean;
    }

    //비밀번호 암호화
    @Bean
    PasswordEncoder getPasswordEncoder() {
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

        //authorizeRequests -> path matching 기반 인증, 인가 설정
        http.authorizeRequests()
                .antMatchers("/user/join.do",
                        "/user/login.do",
                        "/user/find-password.do")
                .permitAll();


        http.authorizeRequests()
                .antMatchers("/admin/**")
                .hasAnyAuthority(UserRole.ADMIN.toString());


        http.formLogin()
                .loginPage("/user/login.do")
                .defaultSuccessUrl("/user/main.do")
                .successHandler(getSuccessHandler())
                .failureHandler(getFailureHandler())
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout.do"))
                .logoutSuccessUrl("/user/login.do")
                .invalidateHttpSession(true); //세션 초기화


        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false) //true인 경우 현재 요청하는 사용자의 인증 실패, false인 경우 기존 사용자의 세션 만료
                .expiredUrl("/user/login.do");

        return http.build();
    }



}
