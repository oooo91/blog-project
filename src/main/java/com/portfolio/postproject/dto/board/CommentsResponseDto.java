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
@Getter
public class CommentsResponseDto {

    private Long commentId;
    private String commentsDetail;
    private String userId;
    private String userName;
    private String createdDate;
    private boolean comparison;

    private static CommentsResponseDto of(PostComments postComments, String userId) {

        return CommentsResponseDto.builder()
                .commentsDetail(postComments.getCommentDetail())
                .commentId(postComments.getId())
                .userName(postComments.getDiaryUser().getNickname())
                .comparison(HostComponents.checkHostComment(postComments, userId))
                .createdDate(postComments.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .build();
    }

    public static List<CommentsResponseDto> of(List<PostComments> list, String userId) {
        List<CommentsResponseDto> commentsResponseDtoList = new ArrayList<>();

        for (PostComments comments : list) {
            commentsResponseDtoList.add(CommentsResponseDto.of(comments, userId));
        }
        return commentsResponseDtoList;
    }
}
