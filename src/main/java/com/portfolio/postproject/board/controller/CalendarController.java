package com.portfolio.postproject.board.controller;

import com.portfolio.postproject.board.components.SortComponents;
import com.portfolio.postproject.board.dto.BoardResponseDto;
import com.portfolio.postproject.board.dto.SortDto;
import com.portfolio.postproject.board.service.CalendarService;
import com.portfolio.postproject.board.components.HostComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final HostComponents hostComponents;
    private final SortComponents sortComponents;

    //캘린터 페이지 이동
    @GetMapping("/board/calendar/{paramId}")
    public String boardCalendar(@PathVariable("paramId") String paramId,
                                Principal principal, Model model, HttpServletRequest request,
                                @PageableDefault(size = 5) Pageable pageable) {

        SortDto sortDto = sortComponents.calendarOf(request);
        Page<BoardResponseDto> list = calendarService.searchCalendar(paramId, sortDto, pageable);

        model.addAttribute("paramId", paramId); //아이디

        model.addAttribute("searchStartDate", sortDto.getSearchStartDate()); //정렬 요소
        model.addAttribute("searchEndDate", sortDto.getSearchEndDate());
        model.addAttribute("sortValue", sortDto.getSortValue());

        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber()); //페이징 정보
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("list", list);
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());
        model.addAttribute("totalCount", list.getTotalElements()); //총 개수
        model.addAttribute("comparison", paramId.equals(principal.getName())); //버튼 유무

        return "/board/calendar";
    }
}
