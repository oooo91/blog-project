package com.portfolio.postproject.repository.board;

import com.portfolio.postproject.entity.board.DiaryWeather;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WeatherRepository extends JpaRepository<DiaryWeather, LocalDate> {

	@Query("select dw.icon from DiaryWeather dw where dw.date = :date")
	String findIconByDate(@Param("date") LocalDate date);
}
