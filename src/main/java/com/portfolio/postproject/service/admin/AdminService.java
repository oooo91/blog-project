package com.portfolio.postproject.service.admin;

import com.portfolio.postproject.dto.admin.AdminDetailStatusResponseDto;
import com.portfolio.postproject.dto.admin.AdminDetailBoardResponseDto;
import com.portfolio.postproject.dto.admin.AdminMainResponseDto;
import com.portfolio.postproject.dto.admin.AdminUpdateStatusRequestDto;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.exception.common.NotFoundUserException;
import com.portfolio.postproject.repository.board.PostRepository;
import com.portfolio.postproject.repository.user.UserRepository;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

	private static AdminMainResponseDto of(DiaryUser diaryUser) {

		return AdminMainResponseDto.builder()
			.userId(diaryUser.getId())
			.nickName(diaryUser.getNickname())
			.createdAt(
				diaryUser.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.userStatus(diaryUser.getUserStatus())
			.userRole(diaryUser.getUserRoles().toString())
			.totalPosts(diaryUser.getDiaryPost().size())
			.build();
	}

	public static List<AdminMainResponseDto> of(List<DiaryUser> list) {
		List<AdminMainResponseDto> adminMainResponseDtoList = new ArrayList<>();

		for (DiaryUser user : list) {
			adminMainResponseDtoList.add(of(user));
		}
		return adminMainResponseDtoList;
	}

	/*main*/
	@Transactional
	public List<AdminMainResponseDto> getUserList(HttpServletRequest request) {

		List<DiaryUser> list;

		if (request.getParameter("searchText") == null ||
			request.getParameter("searchText").trim().equals("")) {
			list = userRepository.findAll();
		} else {
			list = userRepository.findByIdOrNicknameContaining(
				request.getParameter("searchText").trim(),
				request.getParameter("searchText").trim());
		}
		return of(list);
	}

	/*detail*/
	public AdminDetailStatusResponseDto getUserStatus(HttpServletRequest request) {
		DiaryUser diaryUser = userRepository.findById(request.getParameter("userId"))
			.orElseThrow(() -> new NotFoundUserException("사용자가 존재하지 않습니다."));

		return AdminDetailStatusResponseDto.builder()
			.nickName(diaryUser.getNickname())
			.userStatus(diaryUser.getUserStatus())
			.build();
	}

	public List<AdminDetailBoardResponseDto> getUserBoardList(HttpServletRequest request) {
		return AdminDetailBoardResponseDto.of(postRepository.findAllByUserId(request.getParameter("userId")));
	}

	@Transactional
	public void updateUserStatus(AdminUpdateStatusRequestDto adminUpdateStatusRequestDto) {
		DiaryUser diaryUser = userRepository.findById(adminUpdateStatusRequestDto.getUserId())
			.orElseThrow(() -> new NotFoundUserException("사용자가 존재하지 않습니다."));
		diaryUser.setUserStatus(adminUpdateStatusRequestDto.getUserStatus());
	}

	@Transactional
	public void deleteBoard(String postId) {
		postRepository.deleteById(Long.valueOf(postId));
	}
}
