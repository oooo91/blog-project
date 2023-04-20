package com.portfolio.postproject.controller.board;

import com.portfolio.postproject.components.board.SortComponents;
import com.portfolio.postproject.dto.board.BoardResponseDto;
import com.portfolio.postproject.dto.board.SortDto;
import com.portfolio.postproject.service.board.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Pageable;
import java.security.Principal;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CalendarController {

	private final CalendarService calendarService;
	private final SortComponents sortComponents;

	@GetMapping("/board/calendar/{paramId}")
	public String boardCalendar(@PathVariable("paramId") String paramId, Principal principal,
		Model model,
		@RequestParam(value = "searchStartDate", required = false) String searchStartDate,
		@RequestParam(value = "searchEndDate", required = false) String searchEndDate,
		@RequestParam(value = "sortValue", required = false) String sortValue,
		@RequestParam(value = "searchText", required = false) String searchText,
		@PageableDefault(size = 5) Pageable pageable) {

		SortDto sortDto = sortComponents.calendarOf(searchStartDate, searchEndDate, sortValue, searchText);
		Page<BoardResponseDto> page = calendarService.searchCalendar(paramId, sortDto, pageable);

		model.addAttribute("paramId", paramId); //아이디

		model.addAttribute("searchStartDate", sortDto.getSearchStartDate());
		model.addAttribute("searchEndDate", sortDto.getSearchEndDate());
		model.addAttribute("sortValue", sortDto.getSortValue());

		if (page != null) {
			model.addAttribute("list", page);
			model.addAttribute("hasNext", page.hasNext());
			model.addAttribute("hasPrev", page.hasPrevious());
			model.addAttribute("totalCount", page.getTotalElements());
		} else {
			model.addAttribute("totalCount", 0);
		}

		model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
		model.addAttribute("next", pageable.next().getPageNumber());
		model.addAttribute("comparison", paramId.equals(principal.getName()));

		return "/board/calendar";
	}
}
