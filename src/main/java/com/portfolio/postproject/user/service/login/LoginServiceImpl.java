package com.portfolio.postproject.user.service.login;

import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.enums.UserRole;
import com.portfolio.postproject.user.repository.DiaryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final DiaryUserRepository diaryUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<DiaryUser> optionalDiaryUser =  diaryUserRepository.findByUserId(username);
        DiaryUser diaryUser = optionalDiaryUser.get();
        System.out.println(diaryUser.getUserId());

        if(!optionalDiaryUser.isPresent()) {
            throw new UsernameNotFoundException("존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.");
        }

        if(DiaryUser.STATUS_READY.equals(diaryUser.getUserStatus())) {
            throw new UsernameNotFoundException("이메일 활성화 이후에 로그인 하세요");
        }

        if(DiaryUser.STATUS_WITHDRAW.equals(diaryUser.getUserStatus())) {
            throw new UsernameNotFoundException("탈퇴한 회원입니다.");
        }

        //로그인 날짜
        diaryUser.setLoginAt(LocalDateTime.now());
        diaryUserRepository.save(diaryUser);

        //인증 권한 생성
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.USER.toString()));

        //만약 관리자라면 인증 권한 추가
        if(diaryUser.isLevel()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.toString()));
        }

        return new User(diaryUser.getUserId(), diaryUser.getUserPwd(), grantedAuthorities);
    }
}
