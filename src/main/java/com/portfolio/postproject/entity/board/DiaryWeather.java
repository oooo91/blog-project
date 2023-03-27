package com.portfolio.postproject.entity.board;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DiaryWeather {

	@Id
	private LocalDate date;
	private String weather;
	private String icon;
	private double temperature;
}
