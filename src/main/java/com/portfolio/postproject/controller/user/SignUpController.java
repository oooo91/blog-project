package com.portfolio.postproject.controller.user;

import com.portfolio.postproject.service.user.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class SignUpController {

    private final JoinService joinService;

    @GetMapping("/signup")
    public String userJoin() {
        return "/user/signup";
    }

    @GetMapping("/email-auth")
    public String emailAuth(HttpServletRequest request, Model model) {
        model.addAttribute("result", joinService.emailAuth(request.getParameter("uuid")));
        return "/user/email-auth";
    }
}
