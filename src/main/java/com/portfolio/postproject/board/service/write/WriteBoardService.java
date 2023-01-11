package com.portfolio.postproject.board.service.write;

import com.portfolio.postproject.board.dto.BoardDTO;
import com.portfolio.postproject.board.param.PostParam;

public interface WriteBoardService {

    //디테일 페이지
    BoardDTO getDetail(int postId);

    //수정하기
    void updateBoard(PostParam param);

    //삭제하기
    void deleteBoard(int postId);

    //저장하기
    int saveBoard(PostParam param);
}
