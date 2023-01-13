package com.portfolio.postproject.board.dto;

import com.portfolio.postproject.board.entity.DiaryPost;
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
public class BoardDTO {

    private int postId;
    private String postTitle;
    private String postContent;
    private LocalDate postDate; //년,월,일만 필요
    private int year;
    private String month;
    private String day;
    private int numOfComments; //댓글수 조회

    //entity -> dto
    public static BoardDTO of(DiaryPost post) {

        return BoardDTO.builder()
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
    public static List<BoardDTO> of(List<DiaryPost> list) {
        if (list == null) {
            return null;
        }
        List<BoardDTO> diaryDTOlist = new ArrayList<>();

        for (DiaryPost post : list) {
            diaryDTOlist.add(BoardDTO.of(post));
        }
        return diaryDTOlist;
    }

    //Page<entity> -> Page<dto>
    public static Page<BoardDTO> of(Page<DiaryPost> list) {
        if (list == null) {
            return null;
        }
        Page<BoardDTO> newList = list.map(post -> BoardDTO.builder()
                .postId(post.getId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .postDate(post.getPostDate())
                .year(post.getPostDate().getYear())
                .month(String.format("%02d", post.getPostDate().getMonthValue()))
                .day(String.format("%02d", post.getPostDate().getDayOfMonth()))
                .build());

        return newList;
    }
}
