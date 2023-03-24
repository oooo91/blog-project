package com.portfolio.postproject.controller.admin;

import com.portfolio.postproject.service.admin.AdminService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	private final AdminService adminService;

	//관리자 페이지 (권한 설정)
	@GetMapping("/main")
	public String admin(Model model, HttpServletRequest request) {
		model.addAttribute("list", adminService.getUserList(request));
		return "/admin/main";
	}

	//관리자 디테일 페이지 이동 /detail?userId=hyelneu 이런식으로 옴 (권한 설정)
	@GetMapping("/detail")
	public String adminDetail(HttpServletRequest request, Model model) {
		model.addAttribute("userId", request.getParameter("userId")); //페이지에 저장됨 (리로드해도 안사라지게)
		model.addAttribute("boardList", adminService.getUserInfo(request));
		return "/admin/detail";
	}
}
