package com.portfolio.postproject.board.entity;

import com.portfolio.postproject.user.entity.DiaryUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class DiaryPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private DiaryUser diaryUser;

    @NotBlank
    private String postTitle;

    @NotBlank
    private String postContent;

    @NotNull
    private LocalDate postDate; //년,월,일만 필요

    @OneToMany(mappedBy = "diaryPost", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy()
    private List<PostComments> numOfComments; //댓글수 조회

}
