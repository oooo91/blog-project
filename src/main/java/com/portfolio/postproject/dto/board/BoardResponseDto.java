package com.portfolio.postproject.dto.board;

import com.portfolio.postproject.entity.board.DiaryPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BoardResponseDto {

    private long postId;
    private String postTitle;
    private String postContent;
    private String icon;
    private String month;
    private String day;
    private String thumbnailName;
    private int year;
    private int numOfComments;
    private LocalDate postDate;

    public static BoardResponseDto of(DiaryPost post) {

        return BoardResponseDto.builder()
                .postId(post.getId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .icon(post.getIcon())
                .thumbnailName(post.getThumbnailName())
                .postDate(post.getPostDate())
                .year(post.getPostDate().getYear())
                .month(String.format("%02d", post.getPostDate().getMonthValue()))
                .day(String.format("%02d", post.getPostDate().getDayOfMonth()))
                .build();
    }

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
