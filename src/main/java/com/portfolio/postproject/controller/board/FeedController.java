package com.portfolio.postproject.controller.board;

import com.portfolio.postproject.service.board.FeedService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class FeedController {

	private final FeedService feedService;

	@GetMapping("/feed") //뿌리기
	public String main(Model model, Principal principal) {
		model.addAttribute("comparison", feedService.checkAdmin(principal.getName()));
		return "/board/feed";
	}
}
