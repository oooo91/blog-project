package com.portfolio.postproject.user.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/user/main.do")
    public String userMain() {
        return "/user/main";
    }
}
