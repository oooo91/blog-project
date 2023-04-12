package com.portfolio.postproject.dto.board;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FeedResponseDto {

	private String thumbnail;
	private String postTitle;
	private String postContent;
	private LocalDate postLocalDate;
	private String postDate;
	private String userName;
	private String profile;
	private long postId;
	private long numOfComments;

	public FeedResponseDto(String thumbnail, String postTitle, String postContent,
		LocalDate postLocalDate,
		String userName, String profile, long postId, long numOfComments) {
		this.thumbnail = thumbnail;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postDate = postLocalDate.format(DateTimeFormatter.ofPattern("YYYY년 MM월 dd일")); // 형식 바꾸기
		this.userName = userName;
		this.profile = profile;
		this.postId = postId;
		this.numOfComments = numOfComments;
	}
}
