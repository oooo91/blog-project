package com.portfolio.postproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PostProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostProjectApplication.class, args);
    }

}
