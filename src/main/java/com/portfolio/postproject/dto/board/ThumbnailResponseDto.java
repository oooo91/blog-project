package com.portfolio.postproject.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ThumbnailResponseDto {

	String thumbnailName;
	String thumbnail;
}
