package com.portfolio.postproject.board.controller.write;

import com.portfolio.postproject.board.dto.BoardDTO;
import com.portfolio.postproject.board.dto.CommentsDTO;
import com.portfolio.postproject.board.service.comments.CommentsService;
import com.portfolio.postproject.board.service.write.WriteBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WriteController {

    private final WriteBoardService writeBoardService;
    private final CommentsService commentsService;

    //principal과 param 비교 (버튼유무)
    public boolean comparison(String paramId, String userId) {
        return paramId.equals(userId) ? true : false;
    }


    //디테일 페이지 이동
    @GetMapping("/board/detail/{paramId}.do")
    public String boardDetail(@PathVariable("paramId") String paramId,
                              Principal principal, Model model, HttpServletRequest request) {

        //게시판 주인 -> 주인이 아니면 작성/삭제 버튼이 없다.
        boolean comparison = comparison(paramId, principal.getName());

        //post_id로 dto를 받는다.
        long postId = Long.parseLong(request.getParameter("postId"));
        BoardDTO boardDTO = writeBoardService.getDetail(postId);

        //댓글
        List<CommentsDTO> commentsList = commentsService.getComments(postId, principal.getName());

        //내 정보
        String userName = commentsService.getUserName(principal.getName());

        model.addAttribute("userName", userName);
        model.addAttribute("commentsList", commentsList);
        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("comparison", comparison);
        model.addAttribute("paramId", paramId);
        return "/board/detail";
    }


    //수정 페이지 이동
    //접근 권한 확인
    @PreAuthorize("isAuthenticated() and (#paramId == principal.name)")
    @GetMapping("/board/rewrite/{paramId}.do")
    public String boardRewrite(@PathVariable("paramId") String paramId,
                               Principal principal, Model model, HttpServletRequest request) {

        //post_id로 dto를 받는다.
        long postId = Long.parseLong(request.getParameter("postId"));
        BoardDTO boardDTO = writeBoardService.getDetail(postId);

        model.addAttribute("boardDTO", boardDTO);
        model.addAttribute("paramId", paramId);
        return "/board/rewrite";
    }


    //작성 페이지 이동
    @PreAuthorize("isAuthenticated() and (#paramId == principal.name)")
    @GetMapping("/board/write/{paramId}.do")
    public String boardWrite(@PathVariable("paramId") String paramId,
                             Principal principal, Model model) {

        model.addAttribute("paramId", paramId);
        return "/board/write";
    }

}
