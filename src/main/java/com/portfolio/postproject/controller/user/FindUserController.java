package com.portfolio.postproject.controller.user;

import com.portfolio.postproject.service.user.FindUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class FindUserController {

	private final FindUserService findUserService;

	//아이디 찾기
	@GetMapping("/find-user-id-auth")
	public String findUserInfo() { return "/user/find-user-id-auth"; }


	//아이디 인증 표시 페이지
	@GetMapping("/find-user-id")
	public String userFindUserId(Model model, HttpServletRequest request) {

		model.addAttribute("userId",
			findUserService.getUserId(request.getParameter("userEmailAuthKey")));
		return "/user/find-user-id";
	}


	//비밀번호 찾기
	@GetMapping("/find-user-pwd")
	public String findPwd() {
		return "/user/find-user-pwd";
	}


	//비밀번호 인증 표시 페이지
	@GetMapping("/find-user-pwd-auth")
	public String findUserPwdAuth(Model model, HttpServletRequest request) {

		model.addAttribute("userId", request.getParameter("userId"));
		return "/user/find-user-pwd-auth";
	}

}

