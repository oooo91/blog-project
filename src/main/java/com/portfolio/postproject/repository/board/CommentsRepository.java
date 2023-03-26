package com.portfolio.postproject.repository.board;

import com.portfolio.postproject.entity.board.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<PostComments, Long> {

    @Query("select pc from PostComments pc where pc.diaryPost.id = :postId")
    List<PostComments> findAllByPostId(@Param("postId") long postId);
}
