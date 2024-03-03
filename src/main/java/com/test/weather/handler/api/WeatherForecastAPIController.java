package com.test.weather.handler.api;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;

import com.test.weather.model.response.WeatherAPIResponse;
import com.test.weather.service.WeatherForecastService;

import lombok.AllArgsConstructor;

import static com.test.weather.utility.Constants.*;

/**
 * This class is a REST controller in Java that implements an API for retrieving weather forecasts
 * based on a zip code.
 * 
 * @author N Javeed
 */
@RestController
@AllArgsConstructor
public class WeatherForecastAPIController implements WeatherForecastAPI {

    private final WeatherForecastService weatherForecastService;

    /**
     * This function retrieves the weather forecast for a given zip code and returns the forecast along
     * with additional information in a ResponseEntity.
     * 
     * @param zipCode The `zipCode` parameter in the `getWeatherForecast` method is an integer
     * representing the ZIP code for which you want to retrieve the weather forecast.
     * @return The method `getWeatherForecast` returns a `ResponseEntity` object containing a
     * `WeatherAPIResponse` object with weather forecast data, along with HTTP headers and status code.
     * If the weather forecast retrieval is successful, it returns the forecast data with HTTP status
     * OK (200). If an exception occurs during the retrieval process, it returns an empty
     * `WeatherAPIResponse` object with an error message in the
     */
    @Override
    public ResponseEntity<WeatherAPIResponse> getWeatherForecast(@NonNull int zipCode) {
        try {
            WeatherAPIResponse response = weatherForecastService.getForecast(Map.of(ZIP_CODE, String.valueOf(zipCode)));
            HttpHeaders headers = new HttpHeaders();
            headers.add(IS_CACHED, String.valueOf(response.isCached()));
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } catch( Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(ERROR_MESSAGE, e.getLocalizedMessage());
            headers.add(IS_CACHED, "false");
            return new ResponseEntity<>(WeatherAPIResponse.builder().build(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
