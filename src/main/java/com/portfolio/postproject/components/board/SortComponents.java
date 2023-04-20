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

	public SortDto calendarOf(String searchStartDate, String searchEndDate, String sortValue,
		String searchText) {

		if (searchStartDate == null || searchStartDate.trim().equals("")) {

			LocalDate local = LocalDate.now();
			String year = local.getYear() + "년";
			String month = String.format("%02d", local.getMonthValue()) + "월";
			String day = String.format("%02d", local.getDayOfMonth()) + "일";
			String date = year + " " + month + " " + day;

			sortDto.setSearchStartDate(date);
			sortDto.setSearchEndDate(date);

		} else {
			sortDto.setSearchStartDate(searchStartDate);
			sortDto.setSearchEndDate(searchEndDate);
		}

		if (sortValue == null || sortValue.trim().equals("")) {
			sortDto.setSortValue(0);
		} else {
			sortDto.setSortValue(Integer.parseInt(sortValue));
		}

		if (searchText == null || searchText.trim().equals("")) {
			sortDto.setSearchText("");
		} else {
			sortDto.setSearchText(searchText.trim());
		}
		return sortDto;
	}

	public SortDto mainOf(String sortValue, String searchText) {
		if (sortValue == null || sortValue.trim()
			.equals("")) {
			sortDto.setSortValue(0);
		} else {
			sortDto.setSortValue(Integer.parseInt(sortValue));
		}

		if (searchText == null || searchText.trim()
			.equals("")) {
			sortDto.setSearchText("");
		} else {
			sortDto.setSearchText(searchText.trim());
		}

		return sortDto;
	}

	public SortDto feedOf(String searchText) {
		if (searchText == null || searchText.trim()
			.equals("")) {
			sortDto.setSearchText("");
		} else {
			sortDto.setSearchText(searchText.trim());
		}
		return sortDto;
	}
}
