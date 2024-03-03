package com.test.weather.model.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.test.weather.model.enums.Unit;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class WeatherAPIResponse implements Serializable, Cloneable {

    private double currentTemperature;

    private double lowTemperature;

    private double highTemperature;

    @JsonIgnore
    @Builder.Default
    private boolean isCached = true;

    private Unit temperatureUnit;

    private String lastUpdated;

    private List<WeatherAPIResponse> nextForecast;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error cloning WeatherAPIResponse", e);
        }
    }

}
