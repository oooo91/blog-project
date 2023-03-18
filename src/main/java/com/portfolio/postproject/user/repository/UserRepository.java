package com.portfolio.postproject.user.repository;

import com.portfolio.postproject.user.entity.DiaryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<DiaryUser, String> {

    boolean existsByUserEmail(String userEmail);
    Optional<DiaryUser> findByEmailAuthKey(String emailAuthKey);
    Optional<DiaryUser> findByUserEmail(String userEmail);
    Optional<DiaryUser> findByFindIdEmailAuthKey(String userEmailAuthKey);

}
