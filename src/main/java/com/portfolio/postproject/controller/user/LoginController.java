package com.portfolio.postproject.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/user/login")
    public String userLogin() {
        return "/user/login";
    }

}
