package com.portfolio.postproject.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    @GetMapping("/user/myPage/{id}")
    public String userMyPage(@PathVariable("id") String id, Model model) {

        //내 정보 불러오기
        return "/user/myPage";
    }
}
