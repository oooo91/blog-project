package com.portfolio.postproject.user.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginController {

    //로그인 창
    @RequestMapping("/user/login.do")
    public String userLogin() {
        return "/user/login";
    }

}
