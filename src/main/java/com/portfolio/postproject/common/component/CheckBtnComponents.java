package com.portfolio.postproject.common.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckBtnComponents {

    public boolean checkBtn(String userId, String paramId) {
        boolean comparison = true;

        //일치하지 않으면 버튼 ui 삭제
        if (!paramId.equals(userId)) { comparison = false; }

        return comparison;
    }
}
