package com.portfolio.postproject.user.controller.find;

import com.portfolio.postproject.user.exception.FindException;
import com.portfolio.postproject.user.param.find.EmailAuthKeyIdParam;
import com.portfolio.postproject.user.param.find.EmailAuthKeyPwdParam;
import com.portfolio.postproject.user.param.find.EmailAuthPwdParam;
import com.portfolio.postproject.user.service.find.FindUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class FindUserInfoRestController {

    private final FindUserService findUserService;

    @ExceptionHandler(FindException.class)
    public ResponseEntity<String> handlerUserFindException(FindException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
    }

    /*아이디*/
    //아이디 찾기 위한 이메일 인증키 발송
    @PostMapping("/user/find-userId-auth-send.do")
    public ResponseEntity<?> findUserIdAuthSend(@RequestParam String userEmail) {

        //이메일 유무 체크
        findUserService.checkEmailForUserId(userEmail);

        //이메일 있으면 메일로 인증키 전송
        findUserService.sendEmailForUserId(userEmail);

        return ResponseEntity.ok().build();
    }


    //아이디 찾기 위한 이메일 인증키 인증
    @PostMapping("/user/find-userId-auth-check.do")
    public ResponseEntity<?> findUserIdAuthCheck(@RequestBody @Valid EmailAuthKeyIdParam keyParam, Errors errors) {

        String userEmail = keyParam.getUserEmail(); //이메일
        String userAuthKey = keyParam.getUserEmailAuthKey(); //인증키

        //이메일 유효성, 인증키 유효성 체크
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors().stream().map(e -> e.getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }

        //이메일 유무 체크
        findUserService.checkEmailForUserId(userEmail);

        //이메일 인증을 했는지 (인증키를 받았는지), 인증키가 동일한지, 인증키가 유효한지 체크
        findUserService.checkAuthKeyForUserId(userEmail, userAuthKey);

        //비동기로 find-user-id-auth-check.do에 접근하여 성공 시 인증키를 responseEntity에 담는다 ->
        //find-user-id.do에 이동하여 해당 인증키를 가진 아이디를 보여준다.
        return new ResponseEntity<>(userAuthKey, HttpStatus.OK);
    }


    /*비밀번호*/
    //비밀번호 찾기 위해 아이디 체크
    @PostMapping("/user/find-userPwd-checkId.do")
    public ResponseEntity<?> findUserPwdCheckId(@RequestParam String userId) {

        //아이디 유무 체크
        findUserService.checkIdForUserPwd(userId);

        return new ResponseEntity<>(userId, HttpStatus.OK);
    }


    //비밀번호 새 발급을 위한 이메일 인증키 인증
    @PostMapping("/user/find-userPwd-auth-send.do")
    public ResponseEntity<?> findUserPwdAuthSend(@RequestBody @Valid EmailAuthPwdParam param, Errors errors) {

        String userId = param.getUserId();
        String userEmail = param.getUserEmail();

        //이메일 유효성, 아이디 유효성 체크
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors().stream().map(e -> e.getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }

        //해당 아이디와 이메일의 소유주가 동일한지
        findUserService.checkEmailForUserPwd(userId, userEmail);

        //이메일 전송
        findUserService.sendEmailForUserPwd(userEmail);

        return ResponseEntity.ok().build();
    }


    //비밀번호 찾기 위한 이메일 인증키 인증
    @PostMapping("/user/find-userPwd-auth-check.do")
    public ResponseEntity<?> findUserPwdAuthCheck(@RequestBody @Valid EmailAuthKeyPwdParam param, Errors errors) {

        String userId = param.getUserId(); //아이디
        String userEmailAuthKey = param.getUserEmailAuthKey(); //인증키
        String userEmail = param.getUserEmail(); //이메일

        //이메일 유효성, 인증키 유효성 체크
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors.getAllErrors().stream().map(e -> e.getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }

        //해당 아이디와 이메일의 소유주가 동일한지
        findUserService.checkEmailForUserPwd(userId, userEmail);

        //이메일 인증을 했는지 (인증키를 받았는지), 인증키가 동일한지, 인증키가 유효한지 체크
        findUserService.checkAuthKeyForUserPwd(userId, userEmailAuthKey);

        //새 비밀번호 발급
        findUserService.sendNewPwd(userId, userEmail);

        return ResponseEntity.ok().build();
    }


}
