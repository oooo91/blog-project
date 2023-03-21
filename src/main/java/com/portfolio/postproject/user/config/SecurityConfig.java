package com.portfolio.postproject.user.config;

import com.portfolio.postproject.user.handler.UserAuthenticationFailureHandler;
import com.portfolio.postproject.user.handler.UserAuthenticationSuccessHandler;
import com.portfolio.postproject.user.enums.UserRoles;
import com.portfolio.postproject.user.service.LoginService;
import com.portfolio.postproject.user.service.OAuthService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
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
        http
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .sameOrigin() //header ignore

            .and()
            .authorizeRequests()
            .antMatchers("/user/signup",
                                    "/user/login",
                                    "/user/find-password")
                                    .permitAll()
            .antMatchers("/admin/**")
            .hasAnyAuthority(UserRoles.ADMIN.getUserRole())
            .anyRequest().authenticated()

            .and()
            .formLogin()
            .loginPage("/user/login")
            .usernameParameter("username")
            .passwordParameter("password")
            .successHandler(getSuccessHandler())
            .failureHandler(getFailureHandler())

            .and()
            .logout()
            .logoutUrl("/user/logout")
            .logoutSuccessUrl("/user/login")
            .deleteCookies("JSESSIONID")
            .permitAll()

            .and()
            .oauth2Login()
            .userInfoEndpoint()
            .userService(oAuthService)

            .and()
            .successHandler(getSuccessHandler())
            .failureHandler(getFailureHandler())

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

        return http.build();
    }



}
