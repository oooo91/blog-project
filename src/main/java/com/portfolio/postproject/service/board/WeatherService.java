package com.portfolio.postproject.service.board;

import com.portfolio.postproject.entity.board.DiaryWeather;
import com.portfolio.postproject.repository.board.WeatherRepository;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WeatherService {

	@Value("${openweathermap.key}")
	private String apiKey;
	private final WeatherRepository weatherRepository;


	@Transactional
	@Scheduled(cron = "0 0 0 * * *") //매 12시 @Scheduled(cron = "0/5 * * * * *")
	public void saveWeatherDate() {
		weatherRepository.save(getWeatherFormApi());
	}

	private DiaryWeather getWeatherFormApi() {
		String weatherData = getWeatherString(); //1.API
		Map<String, Object> map = parseWeather(weatherData); //2.파싱

		return DiaryWeather.builder() //3.db 저장
			.date(LocalDate.now())
			.weather(map.get("main").toString())
			.icon(map.get("icon").toString())
			.temperature((Double)map.get("temp"))
			.build();
	}

	public String getWeatherString() {
		String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apiKey;

		try {
			URL url = new URL(apiUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			int responseCode = conn.getResponseCode();

			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream())); //실제 응답 객체
			} else {
				br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}

			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			br.close();
			return response.toString();

		} catch (Exception e) {
			return "failed to get response";
		}
	}


	private Map<String, Object> parseWeather(String jsonString) {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject;

		try {
			jsonObject = (JSONObject) jsonParser.parse(jsonString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		Map<String, Object> resultMap = new HashMap<>();

		//temp
		JSONObject mainData = (JSONObject) jsonObject.get("main");
		resultMap.put("temp", mainData.get("temp"));

		//main, icon
		JSONArray weatherArray = (JSONArray) jsonObject.get("weather"); //weather은 array
		JSONObject weatherData = (JSONObject) weatherArray.get(0);
		resultMap.put("main", weatherData.get("main"));
		resultMap.put("icon", weatherData.get("icon"));
		return resultMap;
	}
}
