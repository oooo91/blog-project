package com.portfolio.postproject.controller.user;

import com.portfolio.postproject.service.user.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class MyPageController {

	private final MyPageService myPageService;

	@GetMapping("/myPage/{paramId}")
	public String userMyPage(@PathVariable("paramId") String paramId, Model model) {

		//이름, 이메일, 전체 일기수, 최근 일주일 일기 수 (일요일 기준)
		model.addAttribute("myPageResponseDto", myPageService.getMyPageInfo(paramId));
		model.addAttribute("paramId", paramId);
		return "/user/myPage";
	}

	@GetMapping("/myPage-detail/{paramId}")
	public String userMyPageDetail(@PathVariable("paramId") String paramId, Model model) {

		//아이디, 이메일, 이름, 사진
		model.addAttribute("myPageDetailResponseDto", myPageService.getMyPageDetailInfo(paramId));
		model.addAttribute("paramId", paramId);
		return "/user/myPage-detail";
	}

}
