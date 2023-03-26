package com.portfolio.postproject.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AdminDetailStatusResponseDto {
	private String nickName;
	private String userStatus;
}
