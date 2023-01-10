package com.portfolio.postproject.board.repository;

import com.portfolio.postproject.board.entity.DiaryPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DiaryPostRepository extends JpaRepository<DiaryPost, Integer> {

    //main 페이지 - 오름차순
    @Query("select dp from DiaryPost dp where dp.diaryUser.Id = :paramId and dp.postDate = :date order by dp.Id asc")
    Optional<List<DiaryPost>> findAllByUserIdAndCreatedDateAsc(@Param("paramId") String paramId,
                                                               @Param("date") LocalDate date);

    //main 페이지 - 내림차순
    @Query("select dp from DiaryPost dp where dp.diaryUser.Id = :paramId and dp.postDate = :date order by dp.Id desc")
    Optional<List<DiaryPost>> findAllByUserIdAndCreatedDateDesc(@Param("paramId") String paramId,
                                                                @Param("date") LocalDate date);

    //main 페이지 - 오름차순, 조회
    @Query("select dp from DiaryPost dp where dp.diaryUser.Id = :paramId and dp.postDate = :date and dp.postContent like %:searchText% order by dp.Id asc")
    Optional<List<DiaryPost>> findAllByUserIdAndCreatedDateAsc(@Param("paramId") String paramId,
                                                               @Param("date") LocalDate date,
                                                               @Param("searchText") String searchText);


    //main 페이지 - 내림차순, 조회
    @Query("select dp from DiaryPost dp where dp.diaryUser.Id = :paramId and dp.postDate = :date and dp.postContent like %:searchText% order by dp.Id desc")
    Optional<List<DiaryPost>> findAllByUserIdAndCreatedDateDesc(@Param("paramId") String paramId,
                                                               @Param("date") LocalDate date,
                                                               @Param("searchText") String searchText);
}
