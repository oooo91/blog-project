package com.portfolio.postproject.board.entity;

import com.portfolio.postproject.user.entity.DiaryUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class PostComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private DiaryPost diaryPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DiaryUser diaryUser;

    private String commentDetail;

    private LocalDateTime createdDate;
}
