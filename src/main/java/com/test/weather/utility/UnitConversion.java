package com.test.weather.utility;

import lombok.experimental.UtilityClass;

/**
 * The `UnitConversion` class provides static methods to convert temperature values between Celsius and
 * Fahrenheit.
 * 
 * @author N Javeed
 */
@UtilityClass
public class UnitConversion {
    
    /**
     * The function `celsiusToFahrenheit` converts a temperature value from Celsius to Fahrenheit.
     * 
     * @param celsius The parameter `celsius` in the `celsiusToFahrenheit` method represents a
     * temperature value in Celsius that you want to convert to Fahrenheit. The method takes this
     * Celsius temperature as input and returns the equivalent temperature in Fahrenheit.
     * @return The method `celsiusToFahrenheit` returns the temperature in Fahrenheit after converting
     * the given temperature in Celsius.
     */
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    /**
     * The function fahrenheitToCelsius converts a temperature value from Fahrenheit to Celsius.
     * 
     * @param fahrenheit The parameter `fahrenheit` in the `fahrenheitToCelsius` method represents a
     * temperature value in Fahrenheit that you want to convert to Celsius.
     * @return The method `fahrenheitToCelsius` returns the temperature in Celsius after converting the
     * given temperature from Fahrenheit.
     */
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

}
