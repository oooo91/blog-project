package com.portfolio.postproject.components.board;

import com.portfolio.postproject.dto.board.SortDto;
import java.time.LocalDate;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SortComponents {

	private final SortDto sortDto;

	public SortDto calendarOf(HttpServletRequest request) {

		if (request.getParameter("searchStartDate") == null ||
			request.getParameter("searchStartDate").trim().equals("")) {

			LocalDate local = LocalDate.now();
			String year = local.getYear() + "년";
			String month = String.format("%02d", local.getMonthValue()) + "월";
			String day = String.format("%02d", local.getDayOfMonth()) + "일";
			String date = year + " " + month + " " + day;

			sortDto.setSearchStartDate(date);
			sortDto.setSearchEndDate(date);

		} else {
			sortDto.setSearchStartDate(request.getParameter("searchStartDate"));
			sortDto.setSearchEndDate(request.getParameter("searchEndDate"));
		}

		if (request.getParameter("sortValue") == null ||
			request.getParameter("sortValue").trim().equals("")) {
			sortDto.setSortValue(0);
		} else {
			sortDto.setSortValue(Integer.parseInt(request.getParameter("sortValue")));
		}

		if (request.getParameter("searchText") == null ||
			request.getParameter("searchText").trim().equals("")) {
			sortDto.setSearchText("");
		} else {
			sortDto.setSearchText(request.getParameter("searchText").trim());
		}
		return sortDto;
	}

	public SortDto mainOf(HttpServletRequest request) {
		if (request.getParameter("sortValue") == null || request.getParameter("sortValue").trim()
			.equals("")) {
			sortDto.setSortValue(0);
		} else {
			sortDto.setSortValue(Integer.parseInt(request.getParameter("sortValue")));
		}

		//문자열 조회
		if (request.getParameter("searchText") == null || request.getParameter("searchText").trim()
			.equals("")) {
			sortDto.setSearchText("");
		} else {
			sortDto.setSearchText(request.getParameter("searchText").trim());
		}

		return sortDto;
	}
}
