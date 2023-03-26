package com.portfolio.postproject.service.board;

import com.portfolio.postproject.dto.board.BoardResponseDto;
import com.portfolio.postproject.dto.board.SortDto;
import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.repository.board.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CalendarService {

	private final PostRepository postRepository;

	public Page<BoardResponseDto> searchCalendar(String paramId, SortDto sortDto,
                                                 Pageable pageable) {
		Page<DiaryPost> page;

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		LocalDate startDate = LocalDate.parse(sortDto.getSearchStartDate(), formatter); //2019-12-24
		LocalDate endDate = LocalDate.parse(sortDto.getSearchEndDate(), formatter);

		if (sortDto.getSortValue() == 0) {
			page = postRepository.findByUserIdAndDateAscInCalendar(paramId, startDate,
				endDate, sortDto.getSearchText(), pageable);
		} else {
			page = postRepository.findByUserIdAndDateDescInCalendar(paramId, startDate,
				endDate, sortDto.getSearchText(), pageable);
		}
		return BoardResponseDto.of(page);
	}
}
