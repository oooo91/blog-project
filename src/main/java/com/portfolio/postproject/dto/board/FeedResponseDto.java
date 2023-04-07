package com.portfolio.postproject.dto.board;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FeedResponseDto {

	private String thumbnail;
	private String postTitle;
	private String postContent;
	private LocalDate postDate;
	private String userName;
	private String profile;
	private long numOfComments;

	public FeedResponseDto(String thumbnail, String postTitle, String postContent,
		LocalDate postDate,
		String userName, String profile, long numOfComments) {
		this.thumbnail = thumbnail;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postDate = postDate;
		this.userName = userName;
		this.profile = profile;
		this.numOfComments = numOfComments;
	}
}
