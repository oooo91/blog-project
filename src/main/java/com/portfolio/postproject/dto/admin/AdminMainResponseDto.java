package com.portfolio.postproject.dto.admin;

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
	private String createdAt;
	private String userStatus;
	private String userRole;
	private int totalPosts;
}
