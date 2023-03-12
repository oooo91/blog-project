package com.portfolio.postproject.board.controller;

import com.portfolio.postproject.board.components.HostComponents;
import com.portfolio.postproject.board.service.CommentsService;
import com.portfolio.postproject.board.service.WriteBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class WriteController {

	private final WriteBoardService writeBoardService;
	private final CommentsService commentsService;
	private final HostComponents hostComponents;

	//디테일 페이지 이동
	@GetMapping("/detail/{paramId}")
	public String boardDetail(@PathVariable("paramId") String paramId,
		Principal principal, Model model, HttpServletRequest request) {

		model.addAttribute("userName", commentsService.getUserName(principal));

		model.addAttribute("commentsList", commentsService.getComments(request, principal));
		model.addAttribute("boardDTO", writeBoardService.getDetail(request));
		model.addAttribute("comparison", paramId.equals(principal.getName()));
		model.addAttribute("paramId", paramId);

		return "/board/detail";
	}


	//수정 페이지 이동
	@PreAuthorize("isAuthenticated() and (#paramId == principal.username)")
	@GetMapping("/rewrite/{paramId}")
	public String boardRewrite(@PathVariable("paramId") String paramId,
		Principal principal, Model model, HttpServletRequest request) {

		model.addAttribute("boardDTO", writeBoardService.getDetail(request));
		model.addAttribute("paramId", paramId);

		return "/board/rewrite";
	}


	//작성 페이지 이동
	@PreAuthorize("isAuthenticated() and (#paramId == principal.username)")
	@GetMapping("/write/{paramId}")
	public String boardWrite(@PathVariable("paramId") String paramId,
		Principal principal, Model model) {

		model.addAttribute("paramId", paramId);
		return "/board/write";
	}

}
