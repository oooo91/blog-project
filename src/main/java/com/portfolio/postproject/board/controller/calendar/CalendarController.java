package com.portfolio.postproject.board.controller.calendar;

import com.portfolio.postproject.board.controller.main.MainController;
import com.portfolio.postproject.board.dto.BoardDTO;
import com.portfolio.postproject.board.entity.DiaryPost;
import com.portfolio.postproject.board.service.calendar.CalendarService;
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
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class.getName());
    private final CalendarService calendarService;

    @GetMapping("/board/calendar/{paramId}.do")
    public String boardCalendar(@PathVariable("paramId") String paramId,
                                Principal principal, Model model, HttpServletRequest request,
                                @PageableDefault(size = 5) Pageable pageable) {

        logger.info("calendar sort: " + request.getParameter("sortValue"));

        String userId = principal.getName();
        String sortValue = request.getParameter("sortValue");
        String searchText = request.getParameter("searchText");
        String searchStartDate = request.getParameter("searchStartDate");
        String searchEndDate = request.getParameter("searchEndDate");

        boolean comparison = true; //게시판 주인
        Page<BoardDTO> list; //게시글
        int totalCount = 0; //게시글 수

        //기간 조회 (null인 경우)
        if (searchStartDate == null || searchStartDate.equals(" ")) {
            //오늘 날짜
            LocalDate lacalDate = LocalDate.now();
            String year = lacalDate.getYear() + "년";
            String month = String.format("%02d", lacalDate.getMonthValue()) + "월";
            String day = String.format("%02d", lacalDate.getDayOfMonth()) + "일";
            String date = year + " " + month + " " + day;

            searchStartDate = date;
            searchEndDate = date;
        }

        //오름차(0), 내림차(1) 정렬 (null이 아닌 경우)
        if (sortValue == null || sortValue.equals(" ")) {
            sortValue = String.valueOf(0);
        }

        //문자열 조회 (null인 경우)
        if (searchText == null || searchText.equals(" ")) {
            searchText = "";
        }

        logger.info(searchStartDate);
        logger.info(searchEndDate);

        //list 받아오기
        list = calendarService.searchCalendar(paramId, Integer.parseInt(sortValue),
                searchText, searchStartDate, searchEndDate, pageable);

        //접속한 유저가 해당 게시판 주인이 맞는지 -> 주인이 아니면 작성 버튼이 없다.
        if (!paramId.equals(userId)) { comparison = false;}

        //보여줄 게시글이 있으면 model에 담음
        if (list != null) {
            totalCount = (int) list.getTotalElements();
            model.addAttribute("list", list);
        }

        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("hasNext", list.hasNext());
        model.addAttribute("hasPrev", list.hasPrevious());

        model.addAttribute("searchStartDate", searchStartDate); //시작날짜
        model.addAttribute("searchEndDate", searchEndDate); //마지막날짜

        model.addAttribute("searchText", searchText); //문자열 조회
        model.addAttribute("sortValue", sortValue); //정렬

        model.addAttribute("totalCount", totalCount); //총 개수
        model.addAttribute("comparison", comparison); //버튼 유무
        model.addAttribute("paramId", paramId); //아이디
        return "/board/calendar";
    }
}
