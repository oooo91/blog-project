package com.portfolio.postproject.user.service.find;

import com.portfolio.postproject.board.controller.main.MainController;
import com.portfolio.postproject.common.component.MailComponents;
import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.exception.FindException;
import com.portfolio.postproject.user.repository.DiaryUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUserServiceImpl implements FindUserService {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class.getName());
    private final DiaryUserRepository diaryUserRepository;
    private final MailComponents mailComponents;

    /*아이디*/
    //이메일 유무 체크
    @Override
    public boolean checkEmailForUserId(String userEmail) {
        boolean result = diaryUserRepository.existsByUserEmail(userEmail);

        if (!result) {
            throw new FindException("잘못된 이메일입니다. 다시 입력하세요.");
        }
        return true;
    }


    //이메일 전송
    @Override
    public boolean sendEmailForUserId(String userEmail) {

        //이메일 유무 체크
        DiaryUser user = diaryUserRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new FindException("잘못된 이메일입니다. 다시 입력하세요."));

        //이메일 존재하면 해당 이메일로 아이디 찾기를 위한 인증키 전송, db에 인증키 저장
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,6); //인증키

        //db 저장
        user.setFindIdEmailAuthKey(uuid);
        user.setFindIdLimitDt(LocalDateTime.now().plusDays(1)); //인증키 유효기간
        user.setFindIdEmailAuthYn(false);
        diaryUserRepository.save(user);

        //이메일 전송
        String title = "아이디 확인 메일";
        String contents = "<h3>아이디 확인 메일</h3>" + "<p> 안녕하세요. </p>" +
                "<p> 아이디 확인 창으로 돌아가셔서 아래의 인증키를 입력하세요. </p>" +
                "<p>" + uuid + "</p>" +
                "<p> 아이디 확인 요청을 한 사람이 본인이 아닌 경우, 보안을 위해 고객 센터 ***-*** 로 연락주시기 바랍니다. </p>";

        boolean result = mailComponents.sendEmail(userEmail, title, contents);
        if (!result) {
            throw new FindException("메일 전송에 실패하였습니다.");
        }
        return true;
    }

    //인증키 유무 체크
    @Override
    public boolean checkAuthKeyForUserId(String userEmail, String userEmailAuthKey) {
        Optional<DiaryUser> optional = diaryUserRepository.findByFindIdEmailAuthKey(userEmailAuthKey);

        //인증키 유무
        if (!optional.isPresent()) {
            throw new FindException("인증키를 먼저 발급 받으세요.");
        }
        DiaryUser user = optional.get();

        //인증키 동일한지
        if (!userEmailAuthKey.equals(user.getFindIdEmailAuthKey())) {
            throw new FindException("잘못된 인증번호입니다. 다시 입력하세요.");
        }

        //인증키 유효한지
        if (user.getFindIdLimitDt().isBefore(LocalDateTime.now())) {
            throw new FindException("유효한 날짜가 아닙니다.");
        }

        user.setFindIdEmailAuthYn(true);
        diaryUserRepository.save(user);
        return true;
    }


    //아이디 보여주기
    @Override
    public String getUserId(String userEmailAuthKey) {
        DiaryUser user = diaryUserRepository.findByFindIdEmailAuthKey(userEmailAuthKey).get();
        return user.getId();
    }


    /*비밀번호*/
    //아이디 유무 체크
    @Override
    public boolean checkIdForUserPwd(String userId) {
        diaryUserRepository.findById(userId)
                .orElseThrow(() -> new FindException("아이디가 존재하지 않습니다."));
        return true;
    }

    //비밀번호 찾기 전 해당 아이디의 소유주와 이메일의 소유주가 같은지
    @Override
    public boolean checkEmailForUserPwd(String userId, String userEmail) {
        DiaryUser user = diaryUserRepository.findById(userId).get();

        if (!user.getUserEmail().equals(userEmail)) {
            throw new FindException("이메일이 맞지 않습니다. 다시 입력하세요.");
        }

        return true;

    }

    //이메일 전송
    @Override
    public boolean sendEmailForUserPwd(String userEmail) {

        //이메일 유무 체크
        DiaryUser user = diaryUserRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new FindException("없는 이메일 입니다. 다시 입력하세요."));

        //이메일 존재하면 해당 이메일로 아이디 찾기를 위한 인증키 전송, db에 인증키 저장
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,6); //인증키

        //db 저장
        user.setFindPwdEmailAuthKey(uuid);
        user.setFindPwdLimitDt(LocalDateTime.now().plusDays(1)); //인증키 유효기간
        user.setFindPwdEmailAuthYn(false);
        diaryUserRepository.save(user);

        //이메일 전송
        String title = "비밀번호 인증키 메일";
        String contents = "<h3>비밀번호 인증키 확인 메일</h3>" + "<p> 안녕하세요. </p>" +
                "<p> 비밀번호 확인 창으로 돌아가셔서 아래의 인증키를 입력하세요. </p>" +
                "<p>" + uuid + "</p>" +
                "<p> 비밀번호 확인 요청을 한 사람이 본인이 아닌 경우, 보안을 위해 고객 센터 ***-*** 로 연락주시기 바랍니다. </p>";

        boolean result = mailComponents.sendEmail(userEmail, title, contents);
        if (!result) {
            throw new FindException("메일 전송에 실패하였습니다.");
        }
        return true;
    }

    //인증키 유무 체크
    @Override
    public boolean checkAuthKeyForUserPwd(String userId, String userEmailAuthKey) {
        Optional<DiaryUser> optional = diaryUserRepository.findById(userId);

        //인증키 유무
        if (!optional.isPresent()) {
            throw new FindException("인증키를 먼저 발급 받으세요.");
        }
        DiaryUser user = optional.get();

        //인증키 동일한지
        if (!userEmailAuthKey.equals(user.getFindPwdEmailAuthKey())) {
            throw new FindException("잘못된 인증번호입니다. 다시 입력하세요.");
        }

        //인증키 유효한지
        if (user.getFindPwdLimitDt().isBefore(LocalDateTime.now())) {
            throw new FindException("유효한 날짜가 아닙니다.");
        }

        user.setFindPwdEmailAuthYn(true);
        diaryUserRepository.save(user);
        return true;
    }


    //새 비밀번호 발급 (비밀번호 초기화)
    @Override
    public boolean sendNewPwd(String userId, String userEmail) {
        DiaryUser user = diaryUserRepository.findById(userId).get();

        //이메일 존재하면 해당 이메일로 아이디 찾기를 위한 인증키 전송, db에 인증키 저장
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,10); //인증키

        String enc = BCrypt.hashpw(uuid, BCrypt.gensalt());
        user.setUserPwd("{bcrypt}" + enc);
        diaryUserRepository.save(user);

        //이메일 전송
        String title = "비밀번호 재발급";
        String contents = "<h3>비밀번호 재발급 메일</h3>" + "<p> 안녕하세요. </p>" +
                "<p> 비밀번호가 재발급되었습니다. 아래의 비밀번호로 다시 로그인하세요. </p>" +
                "<p>" + uuid + "</p>" +
                "<p> 비밀번호 재발급 요청을 한 사람이 본인이 아닌 경우, 보안을 위해 고객 센터 ***-*** 로 연락주시기 바랍니다. </p>";

        boolean result = mailComponents.sendEmail(userEmail, title, contents);
        if (!result) {
            throw new FindException("메일 전송에 실패하였습니다.");
        }
        return true;
    }
}
