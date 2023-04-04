package com.portfolio.postproject.entity.board;

import com.portfolio.postproject.entity.user.DiaryUser;
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

    private String postTitle;
    private String postContent;
    private LocalDate postDate;
    private String icon;
    private String thumbnail;
    private String thumbnailName;

    @OneToMany(mappedBy = "diaryPost", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<PostComments> numOfComments;

}
