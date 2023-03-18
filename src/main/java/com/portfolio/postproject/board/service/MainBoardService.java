package com.portfolio.postproject.board.service;

import com.portfolio.postproject.board.dto.BoardResponseDto;
import com.portfolio.postproject.board.dto.SortDto;
import com.portfolio.postproject.board.entity.DiaryPost;
import com.portfolio.postproject.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainBoardService {

    private final PostRepository postRepository;

    public List<BoardResponseDto> boardMain(String paramId, SortDto sortDto) {

        List<DiaryPost> list;

        if (sortDto.getSortValue() == 0) {
            list = postRepository.findAllByUserIdAndDateAscInMain(paramId, sortDto.getSearchText());
        } else {
            list =  postRepository.findAllByUserIdAndDateDescInMain(paramId, sortDto.getSearchText());
        }
        return BoardResponseDto.of(list);
    }
}
