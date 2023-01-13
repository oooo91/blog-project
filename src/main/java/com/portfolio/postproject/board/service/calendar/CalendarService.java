package com.portfolio.postproject.board.service.calendar;

import com.portfolio.postproject.board.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CalendarService {

    //조회 및 정렬 및 기간조회
    Page<BoardDTO> searchCalendar(String paramId,
                                  int sortValue, String searchText,
                                  String searchStartDate, String searchEndDate, Pageable pageable);
}
