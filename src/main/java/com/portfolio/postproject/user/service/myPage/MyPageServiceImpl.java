package com.portfolio.postproject.user.service.myPage;

import com.portfolio.postproject.user.dto.myPage.MyPageDTO;
import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.repository.DiaryUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final DiaryUserRepository diaryUserRepository;

    @Override
    public MyPageDTO findMyPageInfo(String id) {

        //id기반 entity 정보 (name, email)
        Optional<DiaryUser> optionalDiaryUser = diaryUserRepository.findById(id);
        //id기반 작성한 일기 수 -> 다음주

        if(!optionalDiaryUser.isPresent()) {
            return null;
        }
        DiaryUser diaryUser = optionalDiaryUser.get();
        return MyPageDTO.of(diaryUser);
    }
}
