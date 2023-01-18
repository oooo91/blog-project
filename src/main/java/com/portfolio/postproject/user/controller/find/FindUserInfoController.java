package com.portfolio.postproject.user.controller.find;

import com.portfolio.postproject.common.component.FindComponents;
import com.portfolio.postproject.user.service.find.FindUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class FindUserInfoController {

    private final FindUserService findUserService;
    private final FindComponents findComponents;

    //아이디 찾기
    @GetMapping("/user/find-user-id-auth.do")
    public String findUserInfo() {
        return "/user/find-user-id-auth";
    }


    //아이디 인증 표시 페이지
    @GetMapping("/user/find-user-id.do")
    public String userFindUserId(Model model, HttpServletRequest request) {

        //해당 인증키에 대한 아이디 가져오기
        String userId = findUserService.getUserId(request.getParameter("userEmailAuthKey"));

        //아이디 *** 커스텀
        String changeId = findComponents.getChangeId(userId);

        model.addAttribute("userId", changeId);
        return "/user/find-user-id";
    }


    //비밀번호 찾기
    @GetMapping("/user/find-user-pwd-auth-id.do")
    public String findPwd() {
        return "/user/find-user-pwd-auth-id";
    }


    //비밀번호 인증 표시 페이지
    @GetMapping("/user/find-user-pwd-auth.do")
    public String findUserPwdAuth(Model model, HttpServletRequest request) {

        String userId = request.getParameter("userId");
        model.addAttribute("userId", userId);

        return "/user/find-user-pwd-auth";
    }

}

