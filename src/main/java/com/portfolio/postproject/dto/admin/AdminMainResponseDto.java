package com.portfolio.postproject.dto.admin;

import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.entity.user.DiaryUser;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AdminMainResponseDto {
	private String userId;
	private String nickName;
	private LocalDateTime createdAt;
	private String userStatus;
	private int totalPosts;

	private static AdminMainResponseDto of(DiaryUser diaryUser) {

		return AdminMainResponseDto.builder()
			.userId(diaryUser.getId())
			.nickName(diaryUser.getNickname())
			.createdAt(diaryUser.getCreatedAt())
			.userStatus(diaryUser.getUserStatus())
			.totalPosts(diaryUser.getDiaryPost().size())
			.build();
	}

	public static List<AdminMainResponseDto> of(List<DiaryUser> list) {
		List<AdminMainResponseDto> adminMainResponseDtoList = new ArrayList<>();

		for (DiaryUser user : list) {
			adminMainResponseDtoList.add(AdminMainResponseDto.of(user));
		}
		return adminMainResponseDtoList;
	}
}
