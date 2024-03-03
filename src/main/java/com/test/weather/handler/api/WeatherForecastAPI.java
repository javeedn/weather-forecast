package com.test.weather.handler.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.weather.model.response.WeatherAPIResponse;

@RestController
@RequestMapping("/weather-forecast")
public interface WeatherForecastAPI {

    @GetMapping("/v1")
    ResponseEntity<WeatherAPIResponse> getWeatherForecast(@RequestParam int zipCode);
    
}
