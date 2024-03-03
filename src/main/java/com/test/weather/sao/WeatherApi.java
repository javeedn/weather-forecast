package com.test.weather.sao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class WeatherApi {

    @Autowired
    private ObjectMapper mapper;

    public String getWeather(String zipCode) throws JsonProcessingException {
        // Connect with external API 
        Map<String, String> response = Map.of
        ("location", zipCode, 
        "temperature", "27.4", 
        "lowTemperature", "23.0", 
        "highTemperature", "29.2", 
        "unit", "celsius");
        return mapper.writeValueAsString(response);
    }
    
}
