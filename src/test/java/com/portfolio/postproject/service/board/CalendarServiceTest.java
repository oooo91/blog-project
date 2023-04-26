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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
class CalendarServiceTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private CalendarService calendarService;

	@Test
	@DisplayName("달력 조회하기")
	void CalenderServiceTest() {
		//given
		SortDto sortDto = SortDto.builder()
			.sortValue(0)
			.searchText("TEXT")
			.searchStartDate("2023년 04월 01일")
			.searchEndDate("2023년 04월 30일")
			.build();
		Pageable pageable = Pageable.ofSize(5);

		List<DiaryPost> list = new ArrayList<>();
		list.add(DiaryPost.builder()
			.id(15L)
			.postTitle("TITLE")
			.postContent("CONTENT")
			.postDate(LocalDate.now())
			.build());
		Page<DiaryPost> page = new PageImpl<>(list);

		when(postRepository.findAllByUserIdAndDateAscInCalendar(anyString(), any(), any(),
			anyString(), any(pageable.getClass()))).thenReturn(page);

		//when
		Page<BoardResponseDto> response =
			calendarService.searchCalendar("11111", sortDto, pageable);

		//then
		assertEquals("CONTENT", response.getContent().get(0).getPostContent());
		assertEquals(1, page.getTotalPages());
	}
}