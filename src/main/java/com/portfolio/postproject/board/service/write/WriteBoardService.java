package com.portfolio.postproject.board.service.write;

import com.portfolio.postproject.board.dto.BoardDTO;
import com.portfolio.postproject.board.param.PostParam;

public interface WriteBoardService {

    //디테일 페이지
    BoardDTO getDetail(long postId);

    //수정하기
    void updateBoard(PostParam param);

    //삭제하기
    void deleteBoard(long postId);

    //저장하기
    long saveBoard(PostParam param);
}
