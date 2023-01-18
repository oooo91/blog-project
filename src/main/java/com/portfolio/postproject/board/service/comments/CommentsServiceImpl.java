package com.portfolio.postproject.board.service.comments;

import com.portfolio.postproject.board.dto.CommentsDTO;
import com.portfolio.postproject.board.entity.DiaryPost;
import com.portfolio.postproject.board.entity.PostComments;
import com.portfolio.postproject.board.exception.CommentsException;
import com.portfolio.postproject.board.param.CommentsParam;
import com.portfolio.postproject.board.repository.DiaryPostRepository;
import com.portfolio.postproject.board.repository.PostCommentsRepository;
import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.repository.DiaryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentsServiceImpl implements CommentsService {

    private final DiaryPostRepository diaryPostRepository;
    private final PostCommentsRepository postCommentsRepository;
    private final DiaryUserRepository diaryUserRepository;

    //댓글 가져오기
    @Override
    public List<CommentsDTO> getComments(long postId, String userId) {
        List<PostComments> list = postCommentsRepository.findAllByPostId(postId);

        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        return CommentsDTO.of(list, userId);
    }

    //내 정보 가져오기 (닉네임)
    @Override
    public String getUserName(String userId) {
        DiaryUser user = diaryUserRepository.findById(userId).orElseThrow(null);
        return user.getUserName();
    }

    //댓글 작성자 권한 가져오기 및 수정
    @Override
    public boolean updateComments(CommentsParam param, String userId) {
        long commentsId = Long.parseLong(param.getCommentsId());

        PostComments postComments = postCommentsRepository.findById(commentsId)
                .orElseThrow(() -> new CommentsException("이미 삭제된 댓글입니다."));

        if (!postComments.getDiaryUser().getId().equals(userId)) {
            throw new CommentsException("본인이 작성한 댓글만 수정 가능합니다.");
        }

        //댓글 수정
        postComments.setCommentDetail(param.getCommentsDetail());
        postCommentsRepository.save(postComments);

        return true;
    }


    //댓글 작성자 권한 가져오기 및 삭제
    @Override
    public boolean deleteComments(String commentsId, String userId) {
        long id = Long.parseLong(commentsId);

        PostComments postComments = postCommentsRepository.findById(id)
                .orElseThrow(() -> new CommentsException("이미 삭제된 댓글입니다."));

        if (!postComments.getDiaryUser().getId().equals(userId)) {
            throw new CommentsException("본인이 작성한 댓글만 삭제 가능합니다.");
        }

        //댓글 삭제
        postCommentsRepository.delete(postComments);
        return true;
    }


    //댓글 작성자 권한 가져오기 및 저장
    @Override
    public boolean writeComments(CommentsParam param, String userId) {
        if(userId.equals("") || userId == null) {
            throw new CommentsException("먼저 로그인하세요.");
        }

        DiaryUser diaryUser = diaryUserRepository.findById(userId)
                .orElseThrow(() -> new CommentsException("존재하지 않는 회원입니다."));

        long postId = Long.parseLong(param.getPostId());
        DiaryPost diaryPost = diaryPostRepository.findById(postId)
                .orElseThrow(() -> new CommentsException("이미 삭제된 게시글입니다."));

        PostComments postComments = PostComments.builder()
                        .commentDetail(param.getCommentsDetail())
                        .createdDate(LocalDateTime.now())
                        .diaryPost(diaryPost)
                        .diaryUser(diaryUser)
                        .build();
        postCommentsRepository.save(postComments);
        return true;

    }

}
