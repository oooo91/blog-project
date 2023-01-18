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
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService{

    private final DiaryPostRepository diaryPostRepository;

    @Override
    public Page<BoardDTO> searchCalendar(String paramId, Map<String, String> map, Pageable pageable) {
        Page<DiaryPost> page;

        String searchStartDate = map.get("searchStartDate");
        String searchEndDate = map.get("searchEndDate");
        String searchText = map.get("searchText").trim();
        int sortValue = Integer.parseInt(map.get("sortValue"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        LocalDate startDate = LocalDate.parse(searchStartDate, formatter); // 2019-12-24
        LocalDate endDate = LocalDate.parse(searchEndDate, formatter);

        if(sortValue == 0) {
            page = diaryPostRepository.findAllByUserIdAndDateAscInCalendar(paramId, startDate, endDate, searchText, pageable);
        } else {
            page =  diaryPostRepository.findAllByUserIdAndDateDescInCalendar(paramId, startDate, endDate, searchText, pageable);
        }

        //list<entity> -> list<dto>
        return BoardDTO.of(page);
    }
}
