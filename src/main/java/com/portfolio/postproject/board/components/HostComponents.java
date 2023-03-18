package com.portfolio.postproject.board.components;

import com.portfolio.postproject.board.entity.PostComments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HostComponents {

    public static boolean checkHostComment(PostComments comments, String userId) {
        return userId.equals(comments.getDiaryUser().getId());
    }
}
