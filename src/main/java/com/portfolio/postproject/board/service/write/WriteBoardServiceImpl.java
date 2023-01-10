package com.portfolio.postproject.board.service.write;

import com.portfolio.postproject.board.dto.BoardDTO;
import com.portfolio.postproject.board.entity.DiaryPost;
import com.portfolio.postproject.board.exception.PostException;
import com.portfolio.postproject.board.param.PostParam;
import com.portfolio.postproject.board.repository.DiaryPostRepository;
import com.portfolio.postproject.common.config.UserAuthenticationFailureHandler;
import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.repository.DiaryUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WriteBoardServiceImpl implements WriteBoardService {

    private final DiaryPostRepository diaryPostRepository;
    private final DiaryUserRepository diaryUserRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationFailureHandler.class.getName());

    //디테일 페이지
    @Override
    public BoardDTO getDetail(int postId) {
        Optional<DiaryPost> optional = diaryPostRepository.findById(postId);

        if(!optional.isPresent()) {
            return new BoardDTO();
        }

        return BoardDTO.of(optional.get());
    }

    //수정하기
    @Override
    public void updateBoard(PostParam param) {
        Integer postId = Integer.parseInt(param.getPostId());
        Optional<DiaryPost> optional = diaryPostRepository.findById(postId);

        if(!optional.isPresent()) {
            throw new PostException("게시글을 저장할 수 없습니다.");
        }

        DiaryPost diaryPost = optional.get();

        //날짜 형식 String -> Local
        String postDate = param.getPostDate();
        logger.info("updateBoard: " + postDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        LocalDate localDate = LocalDate.parse(postDate, formatter); // 2019-12-24

        //set
        diaryPost.setPostDate(localDate);
        diaryPost.setPostTitle(param.getPostTitle());
        diaryPost.setPostContent(param.getPostContent());

        diaryPostRepository.save(diaryPost);
    }

    //삭제하기
    @Override
    public void deleteBoard(int postId) {
        Optional<DiaryPost> optional = diaryPostRepository.findById(postId);

        if(!optional.isPresent()) {
            throw new PostException("존재하지 않는 게시글입니다.");
        }

        //삭제
        DiaryPost diaryPost = optional.get();
        diaryPostRepository.delete(diaryPost);
    }

    //저장하기
    @Override
    public int saveBoard(PostParam param) {

        //작성자 찾기
        Optional<DiaryUser> optional = diaryUserRepository.findById(param.getParamId());

        if(!optional.isPresent()) {
            throw new PostException("작성자가 존재하지 않습니다.");
        }

        //날짜 형식 String -> Local
        String postDate = param.getPostDate();
        logger.info("updateBoard: " + postDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        LocalDate localDate = LocalDate.parse(postDate, formatter); // 2019-12-24

        DiaryPost diaryPost = DiaryPost.builder()
                .diaryUser(optional.get())
                .postDate(localDate)
                .postContent(param.getPostContent())
                .postTitle(param.getPostTitle())
                .build();

        //저장된 postId값 추출
        DiaryPost takeOutId = diaryPostRepository.save(diaryPost);
        return takeOutId.getId();
    }
}
