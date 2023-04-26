package com.portfolio.postproject.service.board;

import com.portfolio.postproject.dto.board.BoardResponseDto;
import com.portfolio.postproject.dto.board.SortDto;
import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.repository.board.PostRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MainBoardServiceTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private MainBoardService mainBoardService;

	@Test
	@DisplayName("메인")
	void boardMain() {

		SortDto sortDto = SortDto.builder()
			.sortValue(0)
			.searchText("TEXT")
			.searchStartDate("2023년 04월 01일")
			.searchEndDate("2023년 04월 30일")
			.build();

		List<DiaryPost> list = new ArrayList<>();
		list.add(DiaryPost.builder()
			.id(15L)
			.postTitle("TITLE")
			.postContent("CONTENT")
			.postDate(LocalDate.now())
			.build());

		when(postRepository.findAllByUserIdAndDateAscInMain(anyString(), anyString())).thenReturn(list);

		List<BoardResponseDto> response =
			mainBoardService.boardMain("11111", sortDto);

		assertEquals("CONTENT", response.get(0).getPostContent());
	}
}