package com.portfolio.postproject.config;

import com.portfolio.postproject.handler.UserAuthenticationFailureHandler;
import com.portfolio.postproject.handler.UserAuthenticationSuccessHandler;
import com.portfolio.postproject.enums.UserRoles;
import com.portfolio.postproject.service.user.LoginService;
import com.portfolio.postproject.service.user.OAuthService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider bean = new DaoAuthenticationProvider();
        bean.setHideUserNotFoundExceptions(false);
        bean.setUserDetailsService(loginService);
        return bean;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
                                    "/user/find-password",
                                    "/user/email-auth/**",
                                    "/user/find-user-id-auth",
                                    "/user/find-user-id",
                                    "/user/find-user-pwd",
                                    "/user/find-user-pwd-auth")
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
