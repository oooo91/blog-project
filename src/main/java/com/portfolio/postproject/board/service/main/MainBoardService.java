package com.portfolio.postproject.board.service.main;

import com.portfolio.postproject.board.dto.BoardDTO;

import java.util.List;

public interface MainBoardService {

    //main (정렬 및 문자열 조회)
    List<BoardDTO> boardMain(String paramId, int sortValue, String searchtext);
}
