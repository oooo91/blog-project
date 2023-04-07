package com.portfolio.postproject.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MyPageResponseDto {

    private String userName;
    private String userEmail;
    private long totalCount;
    private long mondayCount;
    private long tuesdayCount;
    private long wednesdayCount;
    private long thursdayCount;
    private long fridayCount;
    private long saturdayCount;
    private long sundayCount;

}
