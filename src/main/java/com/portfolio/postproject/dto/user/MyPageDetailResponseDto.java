package com.portfolio.postproject.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MyPageDetailResponseDto {

	private String userName;
	private String userId;
	private String userEmail;
	private String profile;
	private String socialType;
}
