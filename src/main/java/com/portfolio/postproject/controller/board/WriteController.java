package com.portfolio.postproject.controller.board;

import com.portfolio.postproject.service.board.CommentsService;
import com.portfolio.postproject.service.board.WriteBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class WriteController {

	private final WriteBoardService writeBoardService;
	private final CommentsService commentsService;

	@GetMapping("/detail/{paramId}")
	public String boardDetail(@PathVariable("paramId") String paramId,
		Principal principal, Model model, @RequestParam("postId") String postId) {
		model.addAttribute("userName", commentsService.getUserName(principal.getName()));
		model.addAttribute("commentsList", commentsService.getComments(postId, principal.getName()));
		model.addAttribute("boardResponseDto", writeBoardService.getDetail(postId));
		model.addAttribute("comparison", paramId.equals(principal.getName()));
		model.addAttribute("paramId", paramId);

		return "/board/detail";
	}


	@PreAuthorize("isAuthenticated() and (#paramId == principal.name)")
	@GetMapping("/rewrite/{paramId}")
	public String boardRewrite(@PathVariable("paramId") String paramId,
		Principal principal, Model model, @RequestParam("postId") String postId) {

		model.addAttribute("boardResponseDto", writeBoardService.getDetail(postId));
		model.addAttribute("paramId", paramId);

		return "/board/rewrite";
	}


	@PreAuthorize("isAuthenticated() and (#paramId == principal.name)")
	@GetMapping("/write/{paramId}")
	public String boardWrite(@PathVariable("paramId") String paramId, Principal principal,
		Model model) {

		model.addAttribute("paramId", paramId);
		return "/board/write";
	}

}
