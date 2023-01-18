package com.portfolio.postproject.board.service.main;

import com.portfolio.postproject.board.dto.BoardDTO;

import java.util.List;
import java.util.Map;

public interface MainBoardService {

    //main (정렬 및 문자열 조회)
    List<BoardDTO> boardMain(String paramId, Map<String, String> map);
}
