package com.portfolio.postproject.service.user;

import com.portfolio.postproject.dto.board.ThumbnailResponseDto;
import com.portfolio.postproject.dto.user.MyPageDetailPwdRequestDto;
import com.portfolio.postproject.dto.user.MyPageDetailRequestDto;
import com.portfolio.postproject.dto.user.MyPageDetailResponseDto;
import com.portfolio.postproject.dto.user.MyPageResponseDto;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.exception.board.PostException;
import com.portfolio.postproject.exception.common.NotFoundUserException;
import com.portfolio.postproject.exception.user.InvalidPasswordException;
import com.portfolio.postproject.repository.board.PostRepository;
import com.portfolio.postproject.repository.user.UserRepository;
import com.portfolio.postproject.service.board.ThumbnailService;
import java.io.IOException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

	private final UserRepository userRepository;
	private final ThumbnailService thumbnailService;
	private final PostRepository postRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public void myPageDetailUpdate(MyPageDetailRequestDto myPageDetailRequestDto, MultipartFile multipartFile)
		throws IOException {

		DiaryUser diaryUser = userRepository.findById(myPageDetailRequestDto.getParamId())
			.orElseThrow(() -> new NotFoundUserException("사용자가 존재하지 않습니다."));

		diaryUser.setNickname(myPageDetailRequestDto.getUserName());

		if (multipartFile != null) {
			ThumbnailResponseDto thumbnailResponseDto = thumbnailService.uploadImage(multipartFile);
			diaryUser.setProfile(thumbnailResponseDto.getThumbnail());
		}
	}

	@Transactional
	public void myPageDetailPwdUpdate(MyPageDetailPwdRequestDto myPageDetailPwdRequestDto) {

		DiaryUser diaryUser = userRepository.findById(myPageDetailPwdRequestDto.getParamId())
			.orElseThrow(() -> new NotFoundUserException("사용자가 존재하지 않습니다."));

		if (!passwordEncoder.matches(myPageDetailPwdRequestDto.getUserPwd(), diaryUser.getUserPwd())) {
			throw new InvalidPasswordException("아이디 혹은 비밀번호가 잘못되었습니다.");
		}
		diaryUser.setUserPwd(passwordEncoder.encode(myPageDetailPwdRequestDto.getUserNewPwd()));
	}

	@Transactional
	public MyPageResponseDto getMyPageInfo(String paramId) {
		DiaryUser diaryUser = userRepository.findById(paramId)
			.orElseThrow(() -> new PostException("사용자 존재하지 않습니다."));

		return MyPageResponseDto.builder()
			.userName(diaryUser.getNickname())
			.userEmail(diaryUser.getUserEmail())
			.totalCount(postRepository.countTotal(paramId))
			.mondayCount(postRepository.countMonday(paramId))
			.tuesdayCount(postRepository.countTuesday(paramId))
			.wednesdayCount(postRepository.countWednesday(paramId))
			.thursdayCount(postRepository.countThursday(paramId))
			.fridayCount(postRepository.countFriday(paramId))
			.saturdayCount(postRepository.countSaturday(paramId))
			.sundayCount(postRepository.countSunday(paramId))
			.build();
		}

	public MyPageDetailResponseDto getMyPageDetailInfo(String paramId) {
		DiaryUser diaryUser = userRepository.findById(paramId)
			.orElseThrow(() -> new PostException("사용자가 존재하지 않습니다."));

		return MyPageDetailResponseDto.builder()
			.socialType(diaryUser.getSocialType())
			.userName(diaryUser.getNickname())
			.userEmail(diaryUser.getUserEmail())
			.userId(diaryUser.getId())
			.profile(diaryUser.getProfile())
			.build();
	}
}

