package com.portfolio.postproject.common.component;

import com.portfolio.postproject.board.controller.main.MainController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class CalendarComponents {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class.getName());

    //searchStartDate, searchEndDate, searchText, sortValue
    public Map<String, String> sortAndInquire(Map<String, String> map) {

        logger.info("캘린더 시작날짜: " + map.get("searchStartDate"));
        logger.info("캘린더 마지막날짜: " + map.get("searchEndDate"));

        //기간 조회 (null인 경우)
        if (map.get("searchStartDate") == null || map.get("searchStartDate").equals("")) {
            //오늘 날짜
            LocalDate lacalDate = LocalDate.now();
            String year = lacalDate.getYear() + "년";
            String month = String.format("%02d", lacalDate.getMonthValue()) + "월";
            String day = String.format("%02d", lacalDate.getDayOfMonth()) + "일";
            String date = year + " " + month + " " + day;

            map.put("searchStartDate", date);
            map.put("searchEndDate", date);
        }

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
