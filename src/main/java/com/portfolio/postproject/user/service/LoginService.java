package com.portfolio.postproject.user.service;

import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.enums.UserRoles;
import com.portfolio.postproject.user.enums.UserStatus;
import com.portfolio.postproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        DiaryUser diaryUser = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 계정입니다. 회원가입 후 로그인해주세요."));

        if (UserStatus.STATUS_READY.toString().equals(diaryUser.getUserStatus())) {
            throw new UsernameNotFoundException("이메일 활성화 이후에 로그인 하세요");
        }

        if (UserStatus.STATUS_WITHDRAW.toString().equals(diaryUser.getUserStatus())) {
            throw new UsernameNotFoundException("탈퇴한 회원입니다.");
        }

        diaryUser.setLoginAt(LocalDateTime.now()); //login date
        userRepository.save(diaryUser);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(); //granted authority
        grantedAuthorities.add(new SimpleGrantedAuthority(UserRoles.USER.toString()));

        if (diaryUser.isLevel()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(UserRoles.ADMIN.toString())); //만약 관리자라면 인증 권한 추가
        }

        return new User(diaryUser.getId(), diaryUser.getUserPwd(), grantedAuthorities);
    }
}
