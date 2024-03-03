package com.test.weather.handler.api;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.weather.model.enums.Unit;
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
     * This Java function retrieves weather forecast data based on a given zip code and unit, handling
     * exceptions and returning the response with appropriate headers.
     * 
     * @param zipCode The `zipCode` parameter is an integer representing the postal code for which you
     * want to retrieve the weather forecast.
     * @param unit The `unit` parameter in the `getWeatherForecast` method is used to specify the unit
     * of measurement for the weather data. It is typically used to indicate whether the temperature
     * should be displayed in Celsius or Fahrenheit, for example.
     * @return A `ResponseEntity` containing a `WeatherAPIResponse` object, along with headers and an
     * HTTP status code.
     */
    @Override
    public ResponseEntity<WeatherAPIResponse> getWeatherForecast(int zipCode, @RequestParam Unit unit) {
        try {
            WeatherAPIResponse response = weatherForecastService.getForecast(Map.of(ZIP_CODE, String.valueOf(zipCode), UNIT, unit.name()));
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
