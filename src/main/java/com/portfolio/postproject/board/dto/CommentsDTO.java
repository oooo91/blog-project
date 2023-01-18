package com.portfolio.postproject.board.dto;

import com.portfolio.postproject.board.entity.PostComments;
import com.portfolio.postproject.common.component.FindComponents;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentsDTO {

    private Long commentId; //댓글 아이디
    private String commentsDetail; //댓글 내용
    private String userId; //유저 아이디
    private String userName; //유저 이름
    private String createdDate; //년,월,일 시간
    private boolean comparison;

    public static String getChangeId(String userId) {
        StringBuilder sb = new StringBuilder();

        for (int i=0; i<userId.length(); i++) {
            if(i < userId.length()/2) {
                sb.append(userId.charAt(i));
            } else {
                sb.append("*");
            }
        }
        return sb.toString();
    }

    //entity -> dto
    private static CommentsDTO of(PostComments comments, String userId) {

        boolean userYn = false;

        if (userId.equals(comments.getDiaryUser().getId())) {
            userYn = true;
        }

        return CommentsDTO.builder()
                .commentsDetail(comments.getCommentDetail())
                .commentId(comments.getId())
                .userId(getChangeId(comments.getDiaryUser().getId()))
                .userName(comments.getDiaryUser().getUserName())
                .comparison(userYn)
                .createdDate(comments.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .build();
    }

    //list<entity> -> list<dto>
    public static List<CommentsDTO> of(List<PostComments> list, String userId) {
        if (list == null) {
            return null;
        }
        List<CommentsDTO> commentsDTOList = new ArrayList<>();

        for (PostComments comments : list) {
            commentsDTOList.add(CommentsDTO.of(comments, userId));
        }
        return commentsDTOList;
    }

}
