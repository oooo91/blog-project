package com.portfolio.postproject.user.controller.myPage;

import com.portfolio.postproject.user.dto.myPage.MyPageDTO;
import com.portfolio.postproject.user.service.myPage.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/user/myPage/{id}.do")
    public String userMyPage(@PathVariable("id") String id, Model model) {

        //ID가 아니라 Principal

        //내 정보 불러오기
        MyPageDTO myPageInfo = myPageService.findMyPageInfo(id);
        model.addAttribute("myPageInfo", myPageInfo);

        return "/user/myPage";
    }
}
