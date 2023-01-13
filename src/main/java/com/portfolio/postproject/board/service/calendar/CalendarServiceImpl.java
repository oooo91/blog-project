package com.portfolio.postproject.board.service.calendar;

import com.portfolio.postproject.board.dto.BoardDTO;
import com.portfolio.postproject.board.entity.DiaryPost;
import com.portfolio.postproject.board.repository.DiaryPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{

    private final DiaryPostRepository diaryPostRepository;

    @Override
    public Page<BoardDTO> searchCalendar(String paramId,
                                         int sortValue, String searchText,
                                         String searchStartDate, String searchEndDate, Pageable pageable) {

        Page<DiaryPost> page;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        LocalDate startDate = LocalDate.parse(searchStartDate, formatter); // 2019-12-24
        LocalDate endDate = LocalDate.parse(searchEndDate, formatter);
        searchText = searchText.trim();

        if(sortValue == 0) {
            page = diaryPostRepository.findAllByUserIdAndDateAscInCalendar(paramId, startDate, endDate, searchText, pageable);
        } else {
            page =  diaryPostRepository.findAllByUserIdAndDateDescInCalendar(paramId, startDate, endDate, searchText, pageable);
        }


        //list<entity> -> list<dto>
        return BoardDTO.of(page);
    }
}
