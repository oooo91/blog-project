package com.portfolio.postproject.controller.board;

import com.portfolio.postproject.components.board.SortComponents;
import com.portfolio.postproject.dto.board.FeedResponseDto;
import com.portfolio.postproject.dto.board.SortDto;
import com.portfolio.postproject.service.board.FeedService;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class FeedController {

	private final FeedService feedService;
	private final SortComponents sortComponents;

	@GetMapping("/feed")
	public String main(Model model, Principal principal, HttpServletRequest request) {

		SortDto sortDto = sortComponents.feedOf(request);
		List<FeedResponseDto> list = feedService.getFeedInfo(sortDto);

		model.addAttribute("paramId", principal.getName());
		model.addAttribute("comparison", feedService.checkAdmin(principal));
		model.addAttribute("list", list);

		return "/board/feed";
	}
}
