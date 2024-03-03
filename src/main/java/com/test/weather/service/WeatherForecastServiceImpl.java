package com.test.weather.service;

import static com.test.weather.utility.Constants.ZIP_CODE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.weather.model.enums.Unit;
import com.test.weather.model.response.WeatherAPIResponse;
import com.test.weather.sao.WeatherApi;

import lombok.RequiredArgsConstructor;

/**
 * The `WeatherForecastServiceImpl` class is a service implementation that retrieves weather forecasts
 * using an external API, caches the responses, and builds a response object.
 * 
 * @author N Javeed
 */
@Service
@RequiredArgsConstructor
public class WeatherForecastServiceImpl implements WeatherForecastService {

    private final WeatherApi apiClient;

    private static final String WEATHER_CACHE = "weatherCache";

    private final ObjectMapper mapper;

    private final CacheManager cacheManager;

    /**
     * This Java function retrieves weather forecast data either from cache or an external API based on
     * the provided zip code.
     * 
     * @param requestParams The `requestParams` parameter is a `Map<String, String>` that contains the
     * request parameters needed to fetch the weather forecast. In this code snippet, the `zipCode`
     * parameter is extracted from the `requestParams` map to get the weather forecast for a specific
     * location identified by the ZIP code
     * @return The method `getForecast` is returning a `WeatherAPIResponse` object.
     */
    @Override
    public WeatherAPIResponse getForecast(Map<String, String> requestParams) throws JsonProcessingException {
        String zipCode = Optional.ofNullable(requestParams.get(ZIP_CODE))
                          .orElseThrow(() -> new IllegalArgumentException("zipCode is required"));

        String cacheKey = generateCacheKey(zipCode);

        Cache.ValueWrapper valueWrapper = cacheManager.getCache(WEATHER_CACHE).get(cacheKey);
        if (valueWrapper != null) {
            WeatherAPIResponse response = (WeatherAPIResponse) valueWrapper.get();
            response.setCached(true);
            return response;
        }

        String response = apiClient.getWeather(zipCode);

        try {
            WeatherAPIResponse apiResponse = buildResponse(response);
            apiResponse.setNextForecast(List.of((WeatherAPIResponse)apiResponse.clone()));
            cacheManager.getCache(WEATHER_CACHE).put(cacheKey, apiResponse);
            apiResponse.setCached(false);

            return apiResponse;
        } catch (JsonProcessingException | IllegalArgumentException e) {
            throw new RuntimeException("Error processing weather response from external API", e);
        }
    }

    private String generateCacheKey(String zipCode) {
        return "WeatherForecastServiceImpl.getForecast:" + zipCode;
    }

    /**
     * The `buildResponse` function parses a JSON response into a WeatherAPIResponse object with
     * temperature data and unit information.
     * 
     * @param response The `buildResponse` method you provided is responsible for constructing a
     * `WeatherAPIResponse` object from a JSON response string. It uses Jackson's ObjectMapper to map
     * the JSON response to a `Map<String, String>` and then extracts the necessary fields to build the
     * `WeatherAPIResponse` object.
     * @return An instance of `WeatherAPIResponse` is being returned after parsing the input `response`
     * string and mapping its values to the corresponding fields in the `WeatherAPIResponse` object.
     */
    private WeatherAPIResponse buildResponse(String response) throws JsonMappingException, JsonProcessingException {

        Map<String, String> responseObject = mapper.readValue(response, new TypeReference<Map<String, String>>() {});
        
        return WeatherAPIResponse.builder()
                .currentTemperature(Double.parseDouble(responseObject.get("temperature")))
                .lowTemperature(Double.parseDouble(responseObject.get("lowTemperature")))
                .highTemperature(Double.parseDouble(responseObject.get("highTemperature")))
                .temperatureUnit(Enum.valueOf(Unit.class, responseObject.get("unit").toUpperCase()))
                .lastUpdated(String.valueOf(LocalDateTime.now()))
                .isCached(true)
                .build();
    }
    
}
