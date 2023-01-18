package com.portfolio.postproject.board.service.comments;

import com.portfolio.postproject.board.dto.CommentsDTO;
import com.portfolio.postproject.board.param.CommentsParam;

import java.util.List;

public interface CommentsService {
    //댓글 가져오기
    List<CommentsDTO> getComments(long postId, String userId);

    //댓글 내 정보(닉네임) 가져오기
    String getUserName(String userId);

    //댓글 작성자 권한 확인 및 댓글 수정
    boolean updateComments(CommentsParam param, String userId);

    //댓글 작성자 권한 확인 및 댓글 삭제
    boolean deleteComments(String commentsId, String userId);

    //댓글 작성자 권한 확인 및 댓글 저장
    boolean writeComments(CommentsParam param, String userId);
}
