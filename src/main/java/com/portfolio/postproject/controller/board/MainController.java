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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainBoardService mainBoardService;
    private final SortComponents sortComponents;

    //메인 페이지 이동
    @RequestMapping("/board/main/{paramId}")
    public String boardMain(@PathVariable("paramId") String paramId,
                            Principal principal, Model model, HttpServletRequest request) {

        log.info("main 블로그의 아이디: " + paramId);
        log.info("main 세션의 아이디: " + principal.getName());

        SortDto sortDto = sortComponents.mainOf(request);
        List<BoardResponseDto> list = mainBoardService.boardMain(paramId, sortDto);

        model.addAttribute("paramId", paramId);
        model.addAttribute("userId", principal.getName());

        model.addAttribute("list", list);
        model.addAttribute("totalCount", list.size());
        model.addAttribute("sortValue", sortDto.getSortValue());

        model.addAttribute("comparison", paramId.equals(principal.getName()));

        return "/board/main";
    }


}
