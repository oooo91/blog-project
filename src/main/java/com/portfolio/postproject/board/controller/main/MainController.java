package com.portfolio.postproject.board.controller.main;

import com.portfolio.postproject.board.dto.BoardDTO;
import com.portfolio.postproject.board.service.main.MainBoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class.getName());
    private final MainBoardService mainBoardService;

    //main 페이지
    @RequestMapping("/board/main/{paramId}.do")
    public String boardMain(@PathVariable("paramId") String paramId,
                           Principal principal, Model model, HttpServletRequest request) {

        logger.info("param의 아이디: " + paramId);
        logger.info("세션의 아이디: " + principal.getName());

        String userId = principal.getName();
        String getSortValue = request.getParameter("sortValue");
        String getSearchText = request.getParameter("searchText");

        boolean comparison = true; //게시판 주인
        int sortValue = 0; //정렬
        String searchText = ""; //문자열 조회
        List<BoardDTO> list; //게시글
        int totalCount = 0; //게시글 수

        //접속한 유저가 해당 게시판 주인이 맞는지 -> 주인이 아니면 작성 버튼이 없다.
        if (!paramId.equals(userId)) { comparison = false;}

        //오름차(0), 내림차(1) 정렬
        if (getSortValue != null) {
            sortValue = Integer.parseInt(getSortValue);
        } else {
            sortValue = 0;
        }

        //문자열 조회 및 보여줄 게시글
        if (getSearchText != null && getSearchText != " ") {
            searchText = getSearchText;
            list = mainBoardService.boardMain(paramId, sortValue, searchText);
        } else {
            list = mainBoardService.boardMain(paramId, sortValue);
        }

        //보여줄 게시글이 있으면 model에 담음
        if (list.size() > 0 && list != null) {
            totalCount = list.size();
            model.addAttribute("list", list);
        }

        model.addAttribute("searchText", searchText);
        model.addAttribute("sortValue", sortValue);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("comparison", comparison);
        model.addAttribute("paramId", paramId);
        model.addAttribute("userId", userId);
        return "/board/main";
    }


}
