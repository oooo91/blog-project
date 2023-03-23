package com.portfolio.postproject.dto.board;

import com.portfolio.postproject.entity.board.DiaryPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BoardResponseDto {

    private long postId;
    private String postTitle;
    private String postContent;
    private LocalDate postDate; //년,월,일만 필요
    private int year;
    private String month;
    private String day;
    private int numOfComments; //댓글수

    //entity -> dto
    public static BoardResponseDto of(DiaryPost post) {

        return BoardResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postDate(post.getPostDate())
                .year(post.getPostDate().getYear())
                .month(String.format("%02d", post.getPostDate().getMonthValue()))
                .day(String.format("%02d", post.getPostDate().getDayOfMonth()))
                .build();
    }

    //list<entity> -> list<dto>
    public static List<BoardResponseDto> of(List<DiaryPost> list) {
        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        List<BoardResponseDto> diaryDtolist = new ArrayList<>();
        for (DiaryPost post : list) {
            diaryDtolist.add(BoardResponseDto.of(post));
        }
        return diaryDtolist;
    }

    //Page<entity> -> Page<dto>
    public static Page<BoardResponseDto> of(Page<DiaryPost> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.map(post -> BoardResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postDate(post.getPostDate())
                .year(post.getPostDate().getYear())
                .month(String.format("%02d", post.getPostDate().getMonthValue()))
                .day(String.format("%02d", post.getPostDate().getDayOfMonth()))
                .build());
    }
}
