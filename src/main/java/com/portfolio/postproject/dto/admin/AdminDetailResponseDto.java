package com.portfolio.postproject.dto.admin;

import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.entity.user.DiaryUser;
import java.time.LocalDate;
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
public class AdminDetailResponseDto {
	private String nickName;
	private String userStatus;
	private long postId;
	private String postTitle;
	private String postContent;
	private LocalDate postDate;

	public static AdminDetailResponseDto of(DiaryUser diaryUser, DiaryPost diaryPost) {
		return AdminDetailResponseDto.builder()
			.nickName(diaryUser.getNickname())
			.userStatus(diaryUser.getUserStatus())
			.postId(diaryPost.getId())
			.postTitle(diaryPost.getPostTitle())
			.postContent(diaryPost.getPostContent())
			.postDate(diaryPost.getPostDate())
			.build();
	}

	public static List<AdminDetailResponseDto> of(DiaryUser diaryUser, List<DiaryPost> list) {
		List<AdminDetailResponseDto> adminDetailList = new ArrayList<>();

		for (DiaryPost diaryPost : list) {
			adminDetailList.add(AdminDetailResponseDto.of(diaryUser, diaryPost));
		}
		return adminDetailList;
	}
}
