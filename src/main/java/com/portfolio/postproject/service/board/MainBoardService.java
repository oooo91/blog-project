package com.portfolio.postproject.service.board;

import com.portfolio.postproject.dto.board.BoardResponseDto;
import com.portfolio.postproject.dto.board.SortDto;
import com.portfolio.postproject.entity.board.DiaryPost;
import com.portfolio.postproject.repository.board.PostRepository;
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
            list = postRepository.findByUserIdAndDateAscInMain(paramId, sortDto.getSearchText());
        } else {
            list =  postRepository.findByUserIdAndDateDescInMain(paramId, sortDto.getSearchText());
        }
        return BoardResponseDto.of(list);
    }
}
