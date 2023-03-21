package com.portfolio.postproject.user.service;

import com.portfolio.postproject.user.dto.CustomUserDetails;
import com.portfolio.postproject.user.dto.UserSessionDto;
import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.enums.UserStatus;
import com.portfolio.postproject.user.repository.UserRepository;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        DiaryUser diaryUser = userRepository.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정입니다. 회원가입 후 로그인해주세요."));

        if (UserStatus.STATUS_READY.getUserStatus().equals(diaryUser.getUserStatus())) {
            throw new UsernameNotFoundException("이메일 활성화 이후에 로그인 하세요");
        }

        if (UserStatus.STATUS_WITHDRAW.getUserStatus().equals(diaryUser.getUserStatus())) {
            throw new UsernameNotFoundException("탈퇴한 회원입니다.");
        }

        diaryUser.setLoginAt(LocalDateTime.now());
        httpSession.setAttribute("diaryUser", new UserSessionDto(diaryUser)); //여기서 Creation of SecureRandom instance for session ID generation using

        return new CustomUserDetails(diaryUser);

    }
}
