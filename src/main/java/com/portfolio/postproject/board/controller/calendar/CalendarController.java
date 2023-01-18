package com.portfolio.postproject.board.controller.calendar;

import com.portfolio.postproject.board.controller.main.MainController;
import com.portfolio.postproject.board.dto.BoardDTO;
import com.portfolio.postproject.board.service.calendar.CalendarService;
import com.portfolio.postproject.common.component.CalendarComponents;
import com.portfolio.postproject.common.component.CheckBtnComponents;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class.getName());

    private final CalendarService calendarService;
    private final CalendarComponents calendarComponents;
    private final CheckBtnComponents checkBtnValidationComponents;

    //캘린터 페이지 이동
    @GetMapping("/board/calendar/{paramId}.do")
    public String boardCalendar(@PathVariable("paramId") String paramId,
                                Principal principal, Model model, HttpServletRequest request,
                                @PageableDefault(size = 5) Pageable pageable) {

        int totalCount = 0; //게시글 수
        Map<String, String> map = new HashMap<>();

        map.put("sortValue", request.getParameter("sortValue"));
        map.put("searchText", request.getParameter("searchText"));
        map.put("searchStartDate", request.getParameter("searchStartDate"));
        map.put("searchEndDate", request.getParameter("searchEndDate"));

        //기간조회, 문자열 조회, 정렬
        map = calendarComponents.sortAndInquire(map);

        //게시판 주인
        boolean comparison = checkBtnValidationComponents.checkBtn(principal.getName(), paramId);

        //list 받아오기
        Page<BoardDTO> list = calendarService.searchCalendar(paramId, map, pageable);

        //보여줄 게시글이 있으면 model에 담음
        if (list != null) {
            totalCount = (int) list.getTotalElements();
            model.addAttribute("list", list);
        }

        //정렬 요소
        model.addAttribute("searchStartDate", map.get("searchStartDate")); //시작날짜
        model.addAttribute("searchEndDate", map.get("searchEndDate")); //마지막날짜
        model.addAttribute("sortValue", map.get("sortValue")); //정렬
        //버튼 유무
        model.addAttribute("comparison", comparison);
        //페이징 정보
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());
        //총 개수
        model.addAttribute("totalCount", totalCount);
        //아이디
        model.addAttribute("paramId", paramId);

        return "/board/calendar";
    }
}
