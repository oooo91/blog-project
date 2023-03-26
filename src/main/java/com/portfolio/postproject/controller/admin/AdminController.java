package com.portfolio.postproject.controller.admin;

import com.portfolio.postproject.service.admin.AdminService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/main")
	public String admin(Model model, HttpServletRequest request) {

		model.addAttribute("list", adminService.getUserList(request));
		return "/admin/main";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/detail")
	public String adminDetail(HttpServletRequest request, Model model) {

		model.addAttribute("userId", request.getParameter("userId"));
		model.addAttribute("userDto", adminService.getUserStatus(request));
		model.addAttribute("boardList", adminService.getUserBoardList(request));
		return "/admin/detail";
	}
}
