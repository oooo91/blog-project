package com.portfolio.postproject.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user/join.do")
    public String userJoin() {
        return "/user/join";
    }

   @GetMapping("/user/main.do")
    public String userMain() {
        return "/user/main";
   }

}
