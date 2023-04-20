package com.portfolio.postproject.repository.user;

import com.portfolio.postproject.entity.user.DiaryUser;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<DiaryUser, String> {

    boolean existsByUserEmail(String userEmail);
    Optional<DiaryUser> findByEmailAuthKey(String emailAuthKey);
    Optional<DiaryUser> findByUserEmail(String userEmail);
    Optional<DiaryUser> findByFindIdEmailAuthKey(String userEmailAuthKey);
	List<DiaryUser> findByIdOrNicknameContaining(String id, String nickname);
}
