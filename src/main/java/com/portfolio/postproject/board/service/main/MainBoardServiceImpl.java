package com.portfolio.postproject.board.service.main;

import com.portfolio.postproject.board.dto.BoardDTO;
import com.portfolio.postproject.board.entity.DiaryPost;
import com.portfolio.postproject.board.repository.DiaryPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainBoardServiceImpl implements MainBoardService {

    private final DiaryPostRepository diaryPostRepository;

    //정렬&조회
    @Override
    public List<BoardDTO> boardMain(String paramId, Map<String, String> map) {
        List<DiaryPost> list;

        String searchText = map.get("searchText").trim();
        int sortValue = Integer.parseInt(map.get("sortValue"));

        if(sortValue == 0) {
            list = diaryPostRepository.findAllByUserIdAndDateAscInMain(paramId, searchText);
        } else {
            list =  diaryPostRepository.findAllByUserIdAndDateDescInMain(paramId, searchText);
        }

        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        //list<entity> -> list<dto>
        return BoardDTO.of(list);
    }
}
