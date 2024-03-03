package com.test.weather.service;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.weather.model.response.WeatherAPIResponse;

public interface WeatherForecastService {

    public WeatherAPIResponse getForecast(Map<String, String> requestParams) throws JsonProcessingException;
    
}
