package com.portfolio.postproject.components.board;

import com.portfolio.postproject.entity.board.PostComments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HostComponents {

    public static boolean checkHostComment(PostComments comments, String userId) {
        return userId.equals(comments.getDiaryUser().getId());
    }
}
