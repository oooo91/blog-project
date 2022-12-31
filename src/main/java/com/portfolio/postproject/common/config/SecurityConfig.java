package com.portfolio.postproject.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //나중에 테스트 코드에 옮기기,  CSRF 공격 방어를 비활성화
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests().anyRequest().permitAll();
    }
}
