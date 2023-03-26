package com.portfolio.postproject.service.user;

import com.portfolio.postproject.components.user.MailComponents;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.enums.EmailAuth;
import com.portfolio.postproject.enums.UserRoles;
import com.portfolio.postproject.enums.UserStatus;
import com.portfolio.postproject.exception.user.AlreadyExistedUserException;
import com.portfolio.postproject.exception.user.EmailException;
import com.portfolio.postproject.dto.user.EmailAuthResponseDto;
import com.portfolio.postproject.dto.user.JoinRequestDto;
import com.portfolio.postproject.repository.user.UserRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JoinService {

    private final UserRepository userRepository;
    private final MailComponents mailComponents;
    private final PasswordEncoder passwordEncoder;

    public void saveUserInfo(JoinRequestDto joinRequestDto) {

        if (userRepository.existsById(joinRequestDto.getUserId())) {
            throw new AlreadyExistedUserException("이미 가입된 아이디입니다.");
        }

        if (userRepository.existsByNickname(joinRequestDto.getUserName())) {
            throw new AlreadyExistedUserException("이미 가입된 이름입니다.");
        }

        if (userRepository.existsByUserEmail(joinRequestDto.getUserEmail())) {
            throw new AlreadyExistedUserException("이미 가입된 이메일입니다.");
        }

        String uuid = UUID.randomUUID().toString();

        DiaryUser user = DiaryUser.builder()
                .id(joinRequestDto.getUserId())
                .nickname(joinRequestDto.getUserName())
                .userEmail(joinRequestDto.getUserEmail())
                .userPwd(passwordEncoder.encode(joinRequestDto.getUserPwd()))
                .createdAt(LocalDateTime.now())
                .userRoles(UserRoles.USER)
                .socialType("none")
                .emailAuthKey(uuid)
                .emailAuthYn(false)
                .userStatus(UserStatus.STATUS_READY.getUserStatus())
                .build();
        userRepository.save(user);

        String email = joinRequestDto.getUserEmail();
        String title = "diary 사이트 가입을 축하드립니다.";
        String contents = "<p> fastlms 사이트 가입을 축하드립니다. </p>" +
                "<p> 아래 링크를 클릭하셔서 가입을 완료하세요 </p>" +
                "<div>" +
                "<a target='_blank' href='http://localhost:8080/user/email-auth?uuid=" + uuid + "'> 가입완료 </a>" +
                "</div>";

        if (!mailComponents.sendEmail(email, title, contents)) {
            throw new EmailException("메일 전송에 실패하였습니다.");
        }
    }

    @Transactional
    public EmailAuthResponseDto emailAuth(String uuid) {
        Optional<DiaryUser> optionalDiaryUser = userRepository.findByEmailAuthKey(uuid);
        EmailAuthResponseDto emailAuthResponseDto = new EmailAuthResponseDto();

        if (!optionalDiaryUser.isPresent()) {
            emailAuthResponseDto.setEmailAuthStatus(EmailAuth.AUTH_FAIL.getEmailAuth());
            return emailAuthResponseDto;
        }

        DiaryUser diaryUser = optionalDiaryUser.get();

        if (diaryUser.isEmailAuthYn()){
            emailAuthResponseDto.setEmailAuthStatus(EmailAuth.AUTH_DONE.getEmailAuth());
            return emailAuthResponseDto;
        }

        diaryUser.setEmailAuthYn(true);
        diaryUser.setEmailAuthDt(LocalDateTime.now());
        diaryUser.setUserStatus(UserStatus.STATUS_ACTIVE.getUserStatus());

        emailAuthResponseDto.setEmailAuthStatus(EmailAuth.AUTH_SUCCESS.getEmailAuth());
        return emailAuthResponseDto;
    }
}
