package com.portfolio.postproject.controller.board;

import com.portfolio.postproject.components.board.SortComponents;
import com.portfolio.postproject.dto.board.FeedResponseDto;
import com.portfolio.postproject.dto.board.SortDto;
import com.portfolio.postproject.service.board.FeedService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class FeedController {

	private final FeedService feedService;
	private final SortComponents sortComponents;

	@GetMapping("/feed")
	public String feed(Model model, Principal principal,
		@RequestParam(value = "searchText", required = false) String searchText) {

		SortDto sortDto = sortComponents.feedOf(searchText);
		List<FeedResponseDto> list = feedService.getFeedInfo(sortDto);

		model.addAttribute("paramId", principal.getName());
		model.addAttribute("comparison", feedService.checkAdmin(principal.getName()));
		model.addAttribute("list", list);

		return "/board/feed";
	}
}
