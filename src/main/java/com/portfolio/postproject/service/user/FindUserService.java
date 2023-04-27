package com.portfolio.postproject.service.user;

import com.portfolio.postproject.components.user.MailComponents;
import com.portfolio.postproject.components.user.ProtectIdComponents;
import com.portfolio.postproject.dto.user.FindUserInfoRequestDto.EmailAuthToFindId;
import com.portfolio.postproject.dto.user.FindUserInfoRequestDto.EmailAuthToFindPwd;
import com.portfolio.postproject.dto.user.FindUserInfoRequestDto.IdAuthToFindPwd;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.exception.user.EmailException;
import com.portfolio.postproject.exception.common.NotFoundUserException;
import com.portfolio.postproject.repository.user.UserRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindUserService {

    private final UserRepository userRepository;
    private final MailComponents mailComponents;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void sendEmailForUserId(String userEmail) {

        DiaryUser user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new NotFoundUserException("잘못된 이메일입니다. 다시 입력하세요."));

        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,6);

        user.setFindIdEmailAuthKey(uuid);
        user.setFindIdLimitDt(LocalDateTime.now().plusDays(1));
        user.setFindIdEmailAuthYn(false);

        String title = "아이디 확인 메일";
        String contents = "<h3> 아이디 확인 메일 </h3>" + "<p> 안녕하세요. </p>" +
                "<p> 아이디 확인 창으로 돌아가셔서 아래의 인증키를 입력하세요. </p>" +
                "<p>" + uuid + "</p>" +
                "<p> 아이디 확인 요청을 한 사람이 본인이 아닌 경우, 보안을 위해 고객 센터 ***-*** 로 연락주시기 바랍니다. </p>";

        if (!mailComponents.sendEmail(userEmail, title, contents)) {
            throw new EmailException("메일 전송에 실패하였습니다.");
        }
    }


    @Transactional
    public void checkAuthKeyForUserId(EmailAuthToFindId emailAuthToFindId) {

        DiaryUser user = userRepository.findByUserEmail(emailAuthToFindId.getUserEmail())
            .orElseThrow(() -> new NotFoundUserException("잘못된 이메일입니다. 다시 입력하세요."));

        if (!user.getFindIdEmailAuthKey().equals(emailAuthToFindId.getUserEmailAuthKey())) {
            throw new NotFoundUserException("인증키를 먼저 발급 받으세요.");
        }

        if (user.getFindIdLimitDt().isBefore(LocalDateTime.now())) {
            throw new NotFoundUserException("유효한 날짜가 아닙니다.");
        }
        user.setFindIdEmailAuthYn(true);
    }

    @Transactional
    public void sendEmailForUserPwd(IdAuthToFindPwd idAuthToFindPwd) {

        DiaryUser user = userRepository.findById(idAuthToFindPwd.getUserId())
            .orElseThrow(() -> new NotFoundUserException("아이디가 존재하지 않습니다."));

        if (!user.getUserEmail().equals(idAuthToFindPwd.getUserEmail())) {
            throw new NotFoundUserException("이메일이 맞지 않습니다. 다시 입력하세요.");
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,6);

        user.setFindPwdEmailAuthKey(uuid);
        user.setFindPwdLimitDt(LocalDateTime.now().plusDays(1));
        user.setFindPwdEmailAuthYn(false);

        //이메일 전송
        String title = "비밀번호 인증키 메일";
        String contents = "<h3> 비밀번호 인증키 확인 메일 </h3>" + "<p> 안녕하세요. </p>" +
                "<p> 비밀번호 확인 창으로 돌아가셔서 아래의 인증키를 입력하세요. </p>" +
                "<p>" + uuid + "</p>" +
                "<p> 비밀번호 확인 요청을 한 사람이 본인이 아닌 경우, 보안을 위해 고객 센터 ***-*** 로 연락주시기 바랍니다. </p>";

        if (!mailComponents.sendEmail(idAuthToFindPwd.getUserEmail(), title, contents)) {
            throw new EmailException("메일 전송에 실패하였습니다.");
        }
    }


    @Transactional
    public void sendNewPwd(EmailAuthToFindPwd emailAuthToFindPwd) {

        DiaryUser user = userRepository.findById(emailAuthToFindPwd.getUserId())
            .orElseThrow(() -> new NotFoundUserException("아이디가 존재하지 않습니다."));

        if (!user.getUserEmail().equals(emailAuthToFindPwd.getUserEmail())) {
            throw new NotFoundUserException("이메일이 맞지 않습니다. 다시 입력하세요.");
        }

        if (!emailAuthToFindPwd.getUserEmailAuthKey().equals(user.getFindPwdEmailAuthKey())) {
            throw new NotFoundUserException("잘못된 인증번호입니다. 다시 입력하세요.");
        }

        if (user.getFindPwdLimitDt().isBefore(LocalDateTime.now())) {
            throw new NotFoundUserException("유효한 날짜가 아닙니다.");
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0,10);
        user.setUserPwd(passwordEncoder.encode(uuid));

        String title = "비밀번호 재발급";
        String contents = "<h3> 비밀번호 재발급 메일 </h3>" + "<p> 안녕하세요. </p>" +
                "<p> 비밀번호가 재발급되었습니다. 아래의 비밀번호로 다시 로그인하세요. </p>" +
                "<p>" + uuid + "</p>" +
                "<p> 비밀번호 재발급 요청을 한 사람이 본인이 아닌 경우, 보안을 위해 고객 센터 ***-*** 로 연락주시기 바랍니다. </p>";

        if (!mailComponents.sendEmail(emailAuthToFindPwd.getUserEmail(), title, contents)) {
            throw new EmailException("메일 전송에 실패하였습니다.");
        }
    }

    public String getUserId(String userEmailAuthKey) {
        DiaryUser user = userRepository.findByFindIdEmailAuthKey(userEmailAuthKey).get();
        return ProtectIdComponents.getChangeId(user.getId());
    }

    public void checkIdForUserPwd(String userId) {
        userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundUserException("아이디가 존재하지 않습니다."));
    }
}
