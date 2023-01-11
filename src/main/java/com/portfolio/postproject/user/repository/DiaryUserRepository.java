package com.portfolio.postproject.user.repository;

import com.portfolio.postproject.user.entity.DiaryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryUserRepository extends JpaRepository<DiaryUser, String> {

    //JOIN
    boolean existsByUserEmail(String userEmail); //이메일 중복 체크
    Optional<DiaryUser> findByEmailAuthKey(String emailAuthKey); //이메일 인증 체크

    //MYPAGE

}
