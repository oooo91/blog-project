package com.portfolio.postproject.user.service.find;

public interface FindUserService {

    /*아이디*/
    //이메일 유무 체크
    boolean checkEmailForUserId(String userEmail);

    //이메일 전송
    boolean sendEmailForUserId(String userEmail);

    //이메일 인증을 했는가 (인증키를 받았는지), 인증키가 동일한가, 인증키가 유효한가 체크
    boolean checkAuthKeyForUserId(String userEmail, String userAuthKey);

    //아이디 보여주기
    String getUserId(String userEmailAuthKey);


    /*비밀번호*/
    //아이디 유무 체크
    boolean checkIdForUserPwd(String userId);

    //이메일 유무 체크
    boolean checkEmailForUserPwd(String userId, String userEmail);

    //이메일 전송
    boolean sendEmailForUserPwd(String userEmail);

    //이메일 인증을 했는지 (인증키를 받았는지), 인증키가 동일한지, 인증키가 유효한지 체크
    boolean checkAuthKeyForUserPwd(String userId, String userEmailAuthKey);

    //새 비밀번호 발급
    boolean sendNewPwd(String userId, String userEmail);
}

