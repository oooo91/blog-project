package com.portfolio.postproject.user.repository;

import com.portfolio.postproject.user.entity.DiaryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaryUserRepository extends JpaRepository<DiaryUser, String> {
    long countByUserEmail(String userEmail); //이메일 중복 체크
    long countByUserId(String userId); //아이디 중복 체크
    Optional<DiaryUser> findByEmailAuthKey(String emailAuthKey); //이메일 인증 체크
}
