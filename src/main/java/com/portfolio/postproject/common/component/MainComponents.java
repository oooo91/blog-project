package com.portfolio.postproject.common.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class MainComponents {

    //searchText, sortValue
    public Map<String, String> sortAndInquire(Map<String, String> map) {

        //오름차(0), 내림차(1) 정렬 (null이 아닌 경우)
        if (map.get("sortValue") == null || map.get("sortValue").equals("")) {
            map.put("sortValue", String.valueOf(0));
        }

        //문자열 조회 (null인 경우)
        if (map.get("searchText") == null || map.get("searchText").equals("")) {
            map.put("searchText", "");
        }
        return map;
    }
}
