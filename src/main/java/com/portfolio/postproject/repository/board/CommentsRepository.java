package com.portfolio.postproject.repository.board;

import com.portfolio.postproject.entity.board.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<PostComments, Long> {

    //댓글 - 해당 게시글에 대한 댓글, 댓글 작성한 유저 정보
    @Query("select pc from PostComments pc where pc.diaryPost.id = :postId")
    List<PostComments> findAllByPostId(@Param("postId") long postId);
}
