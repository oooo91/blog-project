package com.portfolio.postproject.user.controller.join;

import com.portfolio.postproject.user.param.join.EmailAuthParam;
import com.portfolio.postproject.user.service.join.JoinUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinUserService joinUserService;

    //회원가입 창
    @GetMapping("/user/join.do")
    public String userJoin() {
        return "/user/join";
    }

    //이메일 인증 창
    @GetMapping("/user/email-auth.do")
    public String emailAuth(HttpServletRequest request, Model model) {
        //버튼 누르면 yn이 1이 되고 이제 인증할 수 있다고 알람 보낸다 -> 화면 닫힌다
        String uuid = request.getParameter("uuid");
        System.out.println(uuid);
        EmailAuthParam result = joinUserService.emailAuth(uuid);

        model.addAttribute("result", result.getEmailAuthStatus());

        //모델에 담아서 던지기
        return "/user/email-auth";
    }

}
