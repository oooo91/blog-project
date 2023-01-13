package com.portfolio.postproject.board.service.main;

import com.portfolio.postproject.board.dto.BoardDTO;
import com.portfolio.postproject.board.entity.DiaryPost;
import com.portfolio.postproject.board.repository.DiaryPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MainBoardServiceImpl implements MainBoardService {

    private final DiaryPostRepository diaryPostRepository;

    //정렬&조회
    @Override
    public List<BoardDTO> boardMain(String paramId, int sortValue, String searchText) {
        Optional<List<DiaryPost>> optional;
        searchText = searchText.trim();

        if(sortValue == 0) {
            optional = diaryPostRepository.findAllByUserIdAndDateAscInMain(paramId, LocalDate.now(), searchText);
        } else {
            optional =  diaryPostRepository.findAllByUserIdAndDateDescInMain(paramId, LocalDate.now(), searchText);
        }

        if (!optional.isPresent()) {
            return new ArrayList<>();
        }
        //list<entity> -> list<dto>
        return BoardDTO.of(optional.get());
    }
}
