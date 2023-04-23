package com.portfolio.postproject.controller.user;

import com.portfolio.postproject.service.user.FindUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/find")
public class FindUserController {

	private final FindUserService findUserService;

	//아이디 찾기
	@GetMapping("/user-id-auth")
	public String findUserInfo() {
		return "/user/find/user-id-auth";
	}


	//아이디 인증 표시 페이지
	@GetMapping("/user-id")
	public String userFindUserId(Model model,
		@RequestParam("userEmailAuthKey") String userEmailAuthKey) {

		model.addAttribute("userId",
			findUserService.getUserId(userEmailAuthKey));
		return "/user/find/user-id";
	}


	//비밀번호 찾기
	@GetMapping("/user-pwd")
	public String findPwd() {
		return "/user/find/user-pwd";
	}


	//비밀번호 인증 표시 페이지
	@GetMapping("/user-pwd-auth")
	public String findUserPwdAuth(Model model, @RequestParam("userId") String userId) {

		model.addAttribute("userId", userId);
		return "/user/find/user-pwd-auth";
	}

}

