package com.portfolio.postproject.dto.board;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FeedResponseDto {

	private String thumbnail;
	private String postTitle;
	private String postContent;
	private LocalDate postDate;
	private String userName;
	private String profile;
	private int numOfComments;

}
