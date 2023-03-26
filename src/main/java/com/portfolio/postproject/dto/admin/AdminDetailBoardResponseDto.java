package com.portfolio.postproject.dto.admin;

import com.portfolio.postproject.entity.board.DiaryPost;
import java.time.format.DateTimeFormatter;
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
public class AdminDetailBoardResponseDto {
	private long postId;
	private String postTitle;
	private String postContent;
	private String postDate;

	public static AdminDetailBoardResponseDto of(DiaryPost diaryPost) {
		return AdminDetailBoardResponseDto.builder()
			.postId(diaryPost.getId())
			.postTitle(diaryPost.getPostTitle())
			.postContent(diaryPost.getPostContent())
			.postDate(diaryPost.getPostDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.build();
	}

	public static List<AdminDetailBoardResponseDto> of(List<DiaryPost> list) {
		List<AdminDetailBoardResponseDto> adminDetailList = new ArrayList<>();

		for (DiaryPost diaryPost : list) {
			adminDetailList.add(AdminDetailBoardResponseDto.of(diaryPost));
		}
		return adminDetailList;
	}
}
