package com.portfolio.postproject.controller.board;

import com.portfolio.postproject.components.board.SortComponents;
import com.portfolio.postproject.dto.board.BoardResponseDto;
import com.portfolio.postproject.dto.board.SortDto;
import com.portfolio.postproject.service.board.MainBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

	private final MainBoardService mainBoardService;
	private final SortComponents sortComponents;

	@RequestMapping("/board/main/{paramId}")
	public String boardMain(@PathVariable("paramId") String paramId,
		Principal principal, Model model,
		@RequestParam(value = "sortValue", required = false) String sortValue,
		@RequestParam(value = "searchText", required = false) String searchText) {

		log.info("main 블로그의 아이디: " + paramId);
		log.info("main 세션의 아이디: " + principal.getName());

		SortDto sortDto = sortComponents.mainOf(sortValue, searchText);
		List<BoardResponseDto> list = mainBoardService.boardMain(paramId, sortDto);

		if (list != null) {
			model.addAttribute("list", list);
			model.addAttribute("totalCount", list.size());
			model.addAttribute("sortValue", sortDto.getSortValue());
		} else {
			model.addAttribute("totalCount", 0);
		}

		model.addAttribute("paramId", paramId);
		model.addAttribute("userId", principal.getName());
		model.addAttribute("comparison", paramId.equals(principal.getName()));

		return "/board/main";
	}


}
