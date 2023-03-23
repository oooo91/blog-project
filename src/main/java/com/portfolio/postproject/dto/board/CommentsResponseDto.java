package com.portfolio.postproject.dto.board;

import com.portfolio.postproject.components.board.HostComponents;
import com.portfolio.postproject.entity.board.PostComments;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentsResponseDto {

    private Long commentId; //댓글 아이디
    private String commentsDetail; //댓글 내용
    private String userId; //유저 아이디
    private String userName; //유저 이름
    private String createdDate; //년,월,일 시간
    private boolean comparison;

    //entity -> dto
    private static CommentsResponseDto of(PostComments postComments, String userId) {

        return CommentsResponseDto.builder()
                .commentsDetail(postComments.getCommentDetail())
                .commentId(postComments.getId())
                .userName(postComments.getDiaryUser().getNickname())
                .comparison(HostComponents.checkHostComment(postComments, userId))
                .createdDate(postComments.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .build();
    }

    //list<entity> -> list<dto>
    public static List<CommentsResponseDto> of(List<PostComments> list, String userId) {
        List<CommentsResponseDto> commentsResponseDtoList = new ArrayList<>();

        for (PostComments comments : list) {
            commentsResponseDtoList.add(CommentsResponseDto.of(comments, userId));
        }
        return commentsResponseDtoList;
    }
}
