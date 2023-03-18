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
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private DiaryPost diaryPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private DiaryUser diaryUser;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String commentDetail;

    private LocalDateTime createdDate; //년,월,일 시간
}
