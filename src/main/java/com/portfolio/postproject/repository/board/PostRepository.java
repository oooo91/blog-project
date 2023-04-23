package com.portfolio.postproject.repository.board;

import com.portfolio.postproject.dto.board.FeedResponseDto;
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
    List<DiaryPost> findAllByUserIdAndDateAscInMain(@Param("paramId") String paramId,
                                                              @Param("searchText") String searchText);

    //main 페이지 - 내림차순, 조회
    @Query("select dp from DiaryPost dp where dp.diaryUser.id = :paramId and dp.postContent like %:searchText% order by dp.id desc")
    List<DiaryPost> findAllByUserIdAndDateDescInMain(@Param("paramId") String paramId,
                                                               @Param("searchText") String searchText);

    //calendar 페이지 - 오름차순, 조회
    @Query("select dp from DiaryPost dp where dp.diaryUser.id = :paramId and dp.postDate between :startDate and :endDate and dp.postTitle like %:searchText% order by dp.id asc")
    Page<DiaryPost> findAllByUserIdAndDateAscInCalendar(@Param("paramId") String paramId,
                                                                  @Param("startDate") LocalDate startDate,
                                                                  @Param("endDate") LocalDate endDate,
                                                                  @Param("searchText") String searchText, Pageable pageable);

    //calendar 페이지 - 내림차순, 조회
    @Query("select dp from DiaryPost dp where dp.diaryUser.id = :paramId and dp.postDate between :startDate and :endDate and dp.postTitle like %:searchText% order by dp.id desc")
    Page<DiaryPost> findAllByUserIdAndDateDescInCalendar(@Param("paramId") String paramId,
                                                                  @Param("startDate") LocalDate startDate,
                                                                  @Param("endDate") LocalDate endDate,
                                                                  @Param("searchText") String searchText, Pageable pageable);

    //관리자 페이지
    @Query("select dp from DiaryPost dp where dp.diaryUser.id = :userId")
    List<DiaryPost> findAllByUserId(@Param("userId") String userId);

    //전체
    @Query("select count(*) from DiaryPost dp where dp.diaryUser.id = :paramId")
    long countTotal(@Param("paramId") String paramId);

    //월요일
    @Query("select count(*) from DiaryPost dp where dp.postDate = ADDDATE( CURDATE(), - WEEKDAY(CURDATE()) + 0 ) and dp.diaryUser.id = :paramId")
    long countMonday(@Param("paramId") String paramId);

    @Query("select count(*) from DiaryPost dp where dp.postDate = ADDDATE( CURDATE(), - WEEKDAY(CURDATE()) + 1 ) and dp.diaryUser.id = :paramId")
    long countTuesday(@Param("paramId") String paramId);

    @Query("select count(*) from DiaryPost dp where dp.postDate = ADDDATE( CURDATE(), - WEEKDAY(CURDATE()) + 2 ) and dp.diaryUser.id = :paramId")
    long countWednesday(@Param("paramId") String paramId);

    @Query("select count(*) from DiaryPost dp where dp.postDate = ADDDATE( CURDATE(), - WEEKDAY(CURDATE()) + 3 ) and dp.diaryUser.id = :paramId")
    long countThursday(@Param("paramId") String paramId);

    @Query("select count(*) from DiaryPost dp where dp.postDate = ADDDATE( CURDATE(), - WEEKDAY(CURDATE()) + 4 ) and dp.diaryUser.id = :paramId")
    long countFriday(@Param("paramId") String paramId);

    @Query("select count(*) from DiaryPost dp where dp.postDate = ADDDATE( CURDATE(), - WEEKDAY(CURDATE()) + 5 ) and dp.diaryUser.id = :paramId")
    long countSaturday(@Param("paramId") String paramId);

    @Query("select count(*) from DiaryPost dp where dp.postDate = ADDDATE( CURDATE(), - WEEKDAY(CURDATE()) + 6 ) and dp.diaryUser.id = :paramId")
    long countSunday(@Param("paramId") String paramId);

    //feed
    @Query("select new com.portfolio.postproject.dto.board.FeedResponseDto(dp.thumbnail, dp.postTitle, dp.postContent, dp.postDate, "
        + "du.id, du.nickname, du.profile, dp.id, (select count(*) from PostComments pc where dp.id = pc.diaryPost.id)) "
        + "from DiaryPost dp join fetch DiaryUser du on dp.diaryUser.id = du.id where dp.postContent like %:text% or dp.postTitle like %:text% order by dp.id desc")
    List<FeedResponseDto> findAllFeedResponseDto(@Param("text") String text);
}
