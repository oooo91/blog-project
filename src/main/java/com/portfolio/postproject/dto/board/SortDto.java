package com.portfolio.postproject.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Component
public class SortDto {

    private int sortValue;
    private String searchText;
    private String searchStartDate;
    private String searchEndDate;

}
