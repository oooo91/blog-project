package com.portfolio.postproject.service.admin;

import com.portfolio.postproject.dto.admin.AdminDeleteBoardRequestDto;
import com.portfolio.postproject.dto.admin.AdminDetailResponseDto;
import com.portfolio.postproject.dto.admin.AdminMainResponseDto;
import com.portfolio.postproject.dto.admin.AdminUpdateStatusRequestDto;
import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.exception.user.NotFoundUserException;
import com.portfolio.postproject.repository.board.PostRepository;
import com.portfolio.postproject.repository.user.UserRepository;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;

	public List<AdminMainResponseDto> getUserList(HttpServletRequest request) {
		List<DiaryUser> list = userRepository.findByIdOrNicknameContaining(
			request.getParameter("searchText"), request.getParameter("searchText"));
		return AdminMainResponseDto.of(list);
	}

	public List<AdminDetailResponseDto> getUserInfo(HttpServletRequest request) {
		DiaryUser diaryUser = userRepository.findById(request.getParameter("userId"))
			.orElseThrow(() -> new NotFoundUserException("사용자가 존재하지 않습니다.")); //상태관리

		List<DiaryPost> list = postRepository.findByUserId(request.getParameter("userId")); //글관리
		return AdminDetailResponseDto.of(diaryUser, list);
	}

	@Transactional
	public void updateUserStatus(AdminUpdateStatusRequestDto adminUpdateStatusRequestDto) {
		DiaryUser diaryUser = userRepository.findById(adminUpdateStatusRequestDto.getUserId())
			.orElseThrow(() -> new NotFoundUserException("사용자가 존재하지 않습니다."));
		diaryUser.setUserStatus(adminUpdateStatusRequestDto.getUserStatus());
	}

	@Transactional
	public void deleteBoard(AdminDeleteBoardRequestDto deleteBoardRequestDto) {
		postRepository.deleteById(Long.valueOf(deleteBoardRequestDto.getPostId()));
	}
}
