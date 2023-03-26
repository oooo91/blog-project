package com.portfolio.postproject.service.board;

import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.enums.UserRoles;
import com.portfolio.postproject.exception.common.NotFoundUserException;
import com.portfolio.postproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

	private final UserRepository userRepository;

	public boolean checkAdmin(String name) {
		DiaryUser diaryUser = userRepository.findById(name)
			.orElseThrow(() -> new NotFoundUserException("다시 로그인하세요."));

		log.info("diaryUser의 role: " + diaryUser.getUserRoles());
		log.info("UserRoles의 role: " + UserRoles.ADMIN);

		return diaryUser.getUserRoles().toString().equals(UserRoles.ADMIN.toString());
	}
}
