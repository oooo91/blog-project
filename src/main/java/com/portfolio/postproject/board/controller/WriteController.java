package com.portfolio.postproject.board.controller;

import com.portfolio.postproject.board.service.CommentsService;
import com.portfolio.postproject.board.service.WriteBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

	//디테일 페이지 이동
	@GetMapping("/detail/{paramId}")
	public String boardDetail(@PathVariable("paramId") String paramId,
		Principal principal, Model model, HttpServletRequest request) {

		model.addAttribute("userName", commentsService.getUserName(principal));
		model.addAttribute("commentsList", commentsService.getComments(request, principal));
		model.addAttribute("boardResponseDto", writeBoardService.getDetail(request));
		model.addAttribute("comparison", paramId.equals(principal.getName()));
		model.addAttribute("paramId", paramId);

		return "/board/detail";
	}


	//수정 페이지 이동
	@PreAuthorize("isAuthenticated() and (#paramId == principal.name)")
	@GetMapping("/rewrite/{paramId}")
	public String boardRewrite(@PathVariable("paramId") String paramId,
		Principal principal, Model model, HttpServletRequest request) {

		model.addAttribute("boardResponseDto", writeBoardService.getDetail(request));
		model.addAttribute("paramId", paramId);

		return "/board/rewrite";
	}


	//작성 페이지 이동
	@PreAuthorize("isAuthenticated() and (#paramId.equals(principal.getName()))") //Property or field 'name' cannot be found on object of type 'org.springframework.security.core.userdetails.User'
	@GetMapping("/write/{paramId}")
	public String boardWrite(@PathVariable("paramId") String paramId,
		Authentication authentication, Principal principal, Model model) {

		log.info("principal: " + principal); //OAuth2AuthenticationToken [Principal=Name: [113408037234379197684], Granted Authorities: [[ROLE_SOC...
		log.info("authentication: " + authentication); //OAuth2AuthenticationToken [Principal=Name: [113408037234379197684], Granted Authorit...

		//저 Principal의 name을 꺼내는듯
		log.info("principal의 getName: " + principal.getName()); //113408037234379197684
		log.info("authentication name: " + authentication.getName()); //113408037234379197684

		//저 Principal의 내용을 꺼내는듯
		log.info("authentication principal: " + authentication.getPrincipal()); //Name: [113408037234379197684], Granted Authorities: [[ROLE_SOCIAL]], User ...

		//Token의 찐 클래스를 알려주는 듯
		log.info("뭐가 다르지- getName: " + principal.getClass().getName()); //org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken

		model.addAttribute("paramId", paramId);
		return "/board/write";
	}

}
