package com.portfolio.postproject.user.controller.join;

import com.portfolio.postproject.common.param.ResponseError;
import com.portfolio.postproject.user.exception.JoinException;
import com.portfolio.postproject.user.param.join.EmailAuthParam;
import com.portfolio.postproject.user.param.join.JoinParam;
import com.portfolio.postproject.user.repository.DiaryUserRepository;
import com.portfolio.postproject.user.service.join.JoinUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class JoinRestController {

    private final JoinUserService joinUserService;

    @ExceptionHandler(JoinException.class)
    public ResponseEntity<String> handlerUserJoinException(JoinException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); //400
    }

    //회원가입 시도
    @PostMapping("/user/join.do")
    public ResponseEntity<?> userJoinTry(@RequestBody @Valid JoinParam param, Errors error) {
        //이메일 유효성, 이름, 아이디 유효성, 비밀번호 유효성 체크
        List<ResponseError> joinErrorList = new ArrayList<>();
        boolean result = false;

        if(error.hasErrors()) {
            error.getAllErrors().forEach((e) -> {
                joinErrorList.add(ResponseError.of((FieldError)e));
            });
            return new ResponseEntity<>(joinErrorList, HttpStatus.BAD_REQUEST); //400
        }

        //이메일 중복 체크
        if(joinUserService.checkUserEmail(param.getUserEmail()) > 0) {
            throw new JoinException("이미 가입된 이메일입니다.");
        }

        //아이디 중복 체크
        if(joinUserService.checkUserId(param.getUserId()) > 0) {
            throw new JoinException("이미 가입된 아이디입니다.");
        }

        //비밀번호 암호화하여 DB저장 & 메일 전송 -> 메일 전송 실패할 경우 throw
        if(!joinUserService.saveUserInfo(param)) {
            throw new JoinException("메일 전송에 실패하였습니다.");
        }

        //성공
        return ResponseEntity.ok().build();
    }


}
