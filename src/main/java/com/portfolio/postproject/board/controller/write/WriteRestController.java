package com.portfolio.postproject.board.controller.write;

import com.portfolio.postproject.board.exception.PostException;
import com.portfolio.postproject.board.param.PostParam;
import com.portfolio.postproject.board.service.write.WriteBoardService;
import com.portfolio.postproject.common.param.ResponseError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class WriteRestController {

    private final WriteBoardService writeBoardService;

    //에러 메시지
    @ExceptionHandler(PostException.class)
    public ResponseEntity<String> handlerPostException(PostException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
    }

    //권한 확인 및 수정하기
    @PreAuthorize("isAuthenticated() and (#param.paramId == principal.name)")
    @PostMapping("/board/update.do")
    public ResponseEntity<?> boardUpdate(@RequestBody @Valid PostParam param, Errors error,
                                         Principal principal) {

        //제목, 내용 유효성 체크
        List<ResponseError> postErrorList = new ArrayList<>();

        if (error.hasErrors()) {
            error.getAllErrors().forEach((e) -> {
                postErrorList.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(postErrorList, HttpStatus.BAD_REQUEST); //400
        }

        //postId로 제목, 내용, 날짜 update
        writeBoardService.updateBoard(param);

        //성공
        return ResponseEntity.ok().build();
    }

    //권한 확인 및 삭제하기
    @PreAuthorize("isAuthenticated() and (#paramId == principal.name)")
    @GetMapping("/board/delete/{paramId}.do")
    public ResponseEntity<?> boardDelete(@PathVariable("paramId") String paramId,
                              Principal principal, HttpServletRequest request) {

        //postId로 해당 게시글 삭제
        int postId = Integer.parseInt(request.getParameter("postId"));
        writeBoardService.deleteBoard(postId);

        //성공
        return ResponseEntity.ok().build();
    }

    //권한 확인 및 저장하기
    @PreAuthorize("isAuthenticated() and (#param.paramId == principal.name)")
    @PostMapping("/board/save.do")
    public ResponseEntity<?> boardSave(@RequestBody @Valid PostParam param, Errors error,
                                         Principal principal) {

        //제목, 내용 유효성 체크
        List<ResponseError> postErrorList = new ArrayList<>();

        if (error.hasErrors()) {
            error.getAllErrors().forEach((e) -> {
                postErrorList.add(ResponseError.of((FieldError) e));
            });
            return new ResponseEntity<>(postErrorList, HttpStatus.BAD_REQUEST); //400
        }

        //postId로 제목, 내용, 날짜 save
        int postId = writeBoardService.saveBoard(param);
        String ss = String.valueOf(postId);

        //성공
        return new ResponseEntity<>(ss, HttpStatus.OK);
    }
}
