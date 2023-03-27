package com.portfolio.postproject.repository.board;

import com.portfolio.postproject.entity.board.DiaryWeather;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<DiaryWeather, LocalDate> {

}
