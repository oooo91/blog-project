package com.portfolio.postproject.user.service.join;

import com.portfolio.postproject.user.param.join.EmailAuthParam;
import com.portfolio.postproject.user.param.join.JoinParam;

public interface JoinUserService {

    //이메일 중복 체크
    boolean checkUserEmail(String userEmail);

    //아이디 중복 체크
    boolean checkUserId(String userId);

    //db 저장
    boolean saveUserInfo(JoinParam param);

    //이메일 인증 체크
    EmailAuthParam emailAuth(String uuid);
}
