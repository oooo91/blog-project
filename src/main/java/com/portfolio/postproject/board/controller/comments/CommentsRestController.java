package com.portfolio.postproject.board.controller.comments;


import com.portfolio.postproject.board.exception.CommentsException;
import com.portfolio.postproject.board.param.CommentsParam;
import com.portfolio.postproject.board.service.comments.CommentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentsRestController {

    private final CommentsService commentsService;

    //에러 메시지
    @ExceptionHandler(CommentsException.class)
    public ResponseEntity<String> handlerPostException(CommentsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
    }

    //댓글 수정
    @PostMapping("/comments/update.do")
    public ResponseEntity<?> commentsUpdate(@RequestBody @Valid CommentsParam param, Errors error,
                                            Principal principal) {
        //내용 유효성 체크
        if (error.hasErrors()) {
            List<String> errors = error.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        //댓글 작성자 권한 확인 (댓글 아이디 -> 내 아이디 -> 세션과 같은가 -> 다르면 다른 작성자의 댓글을 수정할 수 없습니다.)
        //권한 확인 후 수정 진행
        commentsService.updateComments(param, principal.getName());

        //성공
        return ResponseEntity.ok().build();
    }

    //댓글 삭제
    @PostMapping("/comments/delete.do")
    public ResponseEntity<?> commentsDelete(@RequestParam String commentsId, Principal principal) {

        //댓글 작성자 권한 확인, 권한 확인 후 삭제 진행
        commentsService.deleteComments(commentsId, principal.getName());

        //성공
        return ResponseEntity.ok().build();
    }

    //댓글 작성
    @PostMapping("/comments/write.do")
    public ResponseEntity<?> commentsWrite(@RequestBody @Valid CommentsParam param, Errors error,
                                           Principal principal) {
        //내용 유효성 체크
        if (error.hasErrors()) {
            List<String> errors = error.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        //댓글 작성자 권한 확인, 권한 확인 후 저장 진행
        commentsService.writeComments(param, principal.getName());

        //설공
        return ResponseEntity.ok().build();
    }
}
