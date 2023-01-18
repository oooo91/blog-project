package com.portfolio.postproject.board.controller.main;

import com.portfolio.postproject.board.dto.BoardDTO;
import com.portfolio.postproject.board.service.main.MainBoardService;
import com.portfolio.postproject.common.component.CheckBtnComponents;
import com.portfolio.postproject.common.component.MainComponents;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class.getName());
    private final MainBoardService mainBoardService;
    private final MainComponents mainComponents;
    private final CheckBtnComponents checkBtnValidationComponents;

    //메인 페이지 이동
    @RequestMapping("/board/main/{paramId}.do")
    public String boardMain(@PathVariable("paramId") String paramId,
                           Principal principal, Model model, HttpServletRequest request) {

        logger.info("main param의 아이디: " + paramId);
        logger.info("main 세션의 아이디: " + principal.getName());

        int totalCount = 0; //게시글 수
        Map<String, String> map = new HashMap<>();

        map.put("sortValue", request.getParameter("sortValue"));
        map.put("searchText", request.getParameter("searchText"));

        //기간조회, 문자열 조회, 정렬
        map = mainComponents.sortAndInquire(map);

        //게시판 주인
        boolean comparison = checkBtnValidationComponents.checkBtn(principal.getName(), paramId);

        //리스트
        List<BoardDTO> list = mainBoardService.boardMain(paramId, map);

        if (list != null) {
            totalCount = list.size();
            model.addAttribute("list", list);
        }

        model.addAttribute("sortValue", map.get("sortValue"));
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("comparison", comparison);
        model.addAttribute("paramId", paramId);
        model.addAttribute("userId", principal.getName());

        return "/board/main";
    }


}
