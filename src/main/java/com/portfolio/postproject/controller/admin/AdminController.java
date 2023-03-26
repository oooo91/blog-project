package com.portfolio.postproject.controller.admin;

import com.portfolio.postproject.service.admin.AdminService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	private final AdminService adminService;

	//관리자 페이지 (권한 설정)
	@GetMapping("/main")
	public String admin(Model model, HttpServletRequest request) {

		model.addAttribute("list", adminService.getUserList(request));
		return "/admin/main";
	}

	//관리자 디테일 페이지 이동 (권한 설정)
	@GetMapping("/detail")
	public String adminDetail(HttpServletRequest request, Model model) {

		model.addAttribute("userId", request.getParameter("userId"));
		model.addAttribute("userDto", adminService.getUserStatus(request));
		model.addAttribute("boardList", adminService.getUserBoardList(request));
		return "/admin/detail";
	}
}
