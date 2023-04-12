package com.portfolio.postproject.service.board;

import com.portfolio.postproject.dto.board.FeedResponseDto;
import com.portfolio.postproject.dto.board.SortDto;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.enums.UserRoles;
import com.portfolio.postproject.exception.common.NotFoundUserException;
import com.portfolio.postproject.repository.board.PostRepository;
import com.portfolio.postproject.repository.user.UserRepository;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;

	public boolean checkAdmin(Principal principal) {

		DiaryUser diaryUser = userRepository.findById(principal.getName())
			.orElseThrow(() -> new NotFoundUserException("다시 로그인하세요."));
		log.info("user's role: " + diaryUser.getUserRoles());

		return diaryUser.getUserRoles().toString().equals(UserRoles.ADMIN.toString());
	}

	public List<FeedResponseDto> getFeedInfo(SortDto sortDto) {
		return postRepository.findAllFeedResponseDto(sortDto.getSearchText());
	}
}
