package com.portfolio.postproject.repository.board;

import com.portfolio.postproject.entity.board.DiaryPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<DiaryPost, Long> {

    //main 페이지 - 오름차순, 조회
    @Query("select dp from DiaryPost dp where dp.diaryUser.id = :paramId and dp.postContent like %:searchText% order by dp.id asc")
    List<DiaryPost> findByUserIdAndDateAscInMain(@Param("paramId") String paramId,
                                                              @Param("searchText") String searchText);

    //main 페이지 - 내림차순, 조회
    @Query("select dp from DiaryPost dp where dp.diaryUser.id = :paramId and dp.postContent like %:searchText% order by dp.id desc")
    List<DiaryPost> findByUserIdAndDateDescInMain(@Param("paramId") String paramId,
                                                               @Param("searchText") String searchText);

    //calendar 페이지 - 오름차순, 조회
    @Query("select dp from DiaryPost dp where dp.diaryUser.id = :paramId and dp.postDate between :startDate and :endDate and dp.postTitle like %:searchText% order by dp.id asc")
    Page<DiaryPost> findByUserIdAndDateAscInCalendar(@Param("paramId") String paramId,
                                                                  @Param("startDate") LocalDate startDate,
                                                                  @Param("endDate") LocalDate endDate,
                                                                  @Param("searchText") String searchText, Pageable pageable);

    //calendar 페이지 - 내림차순, 조회
    @Query("select dp from DiaryPost dp where dp.diaryUser.id = :paramId and dp.postDate between :startDate and :endDate and dp.postTitle like %:searchText% order by dp.id desc")
    Page<DiaryPost> findByUserIdAndDateDescInCalendar(@Param("paramId") String paramId,
                                                                  @Param("startDate") LocalDate startDate,
                                                                  @Param("endDate") LocalDate endDate,
                                                                  @Param("searchText") String searchText, Pageable pageable);

    //admin detail 페이지
    @Query("select dp from DiaryPost dp where dp.diaryUser.id = :userId")
    List<DiaryPost> findByUserId(@Param("userId") String userId);
}
