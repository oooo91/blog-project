package com.portfolio.postproject.user.service.join;

import com.portfolio.postproject.common.component.MailComponents;
import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.enums.EmailAuth;
import com.portfolio.postproject.user.enums.UserStatus;
import com.portfolio.postproject.user.exception.JoinException;
import com.portfolio.postproject.user.param.join.EmailAuthParam;
import com.portfolio.postproject.user.param.join.JoinParam;
import com.portfolio.postproject.user.repository.DiaryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JoinUserServiceImpl implements JoinUserService {

    private final DiaryUserRepository diaryUserRepository;
    private final MailComponents mailComponents;

    //이메일 중복 체크 (true면 에러)
    @Override
    public boolean checkUserEmail(String userEmail) {

        boolean result = diaryUserRepository.existsByUserEmail(userEmail);
        if (result) {
            throw new JoinException("이미 가입된 이메일입니다.");
        }
        return false;
    }

    //아이디 중복 체크
    @Override
    public boolean checkUserId(String userId) {
        boolean result = diaryUserRepository.existsById(userId);
        if (result) {
            throw new JoinException("이미 가입된 아이디입니다.");
        }
        return false;
    }

    //비밀번호 암호화, 이메일 인증키값 DB저장, 이메일 전송
    @Override
    public boolean saveUserInfo(JoinParam param) {

        String uuid = UUID.randomUUID().toString();
        String enc = BCrypt.hashpw(param.getUserPwd(), BCrypt.gensalt());

        //DB저장
        DiaryUser user = DiaryUser.builder()
                .id(param.getUserId())
                .userName(param.getUserName())
                .userEmail(param.getUserEmail())
                .userPwd("{bcrypt}" + enc) //비밀번호 저장방식 바꿈
                .createdAt(LocalDateTime.now())
                .level(false)
                .emailAuthKey(uuid)
                .emailAuthYn(false)
                .userStatus(UserStatus.STATUS_READY.toString())
                .build();
        diaryUserRepository.save(user);

        //이메일 전송
        String email = param.getUserEmail();
        String title = "diary 사이트 가입을 축하드립니다.";
        String contents = "<p> fastlms 사이트 가입을 축하드립니다. </p>" +
                "<p> 아래 링크를 클릭하셔서 가입을 완료하세요 </p>" +
                "<div>" +
                "<a target='_blank' href='http://localhost:8080/user/email-auth.do?uuid=" + uuid + "'> 가입완료 </a>" +
                "</div>";

        boolean result = mailComponents.sendEmail(email, title, contents);
        if (!result) {
            throw new JoinException("메일 전송에 실패하였습니다.");
        }
        return true;
    }

    //이메일 인증 체크 및 유저 상태 활성화
    @Override
    public EmailAuthParam emailAuth(String uuid) {
        Optional<DiaryUser> optionalDiaryUser = diaryUserRepository.findByEmailAuthKey(uuid);
        EmailAuthParam emailAuthParam = new EmailAuthParam();
        System.out.println(optionalDiaryUser);

        if (!optionalDiaryUser.isPresent()) {
            emailAuthParam.setEmailAuthStatus(EmailAuth.AUTH_FAIL.toString());
            return emailAuthParam;
        }

        DiaryUser diaryUser = optionalDiaryUser.get();

        if (diaryUser.isEmailAuthYn()){
            emailAuthParam.setEmailAuthStatus(EmailAuth.AUTH_DONE.toString());
            return emailAuthParam;
        }

        diaryUser.setEmailAuthYn(true);
        diaryUser.setEmailAuthDt(LocalDateTime.now());
        diaryUser.setUserStatus(UserStatus.STATUS_ACTIVE.toString());
        diaryUserRepository.save(diaryUser);

        emailAuthParam.setEmailAuthStatus(EmailAuth.AUTH_SUCCESS.toString());
        return emailAuthParam;
    }
}
