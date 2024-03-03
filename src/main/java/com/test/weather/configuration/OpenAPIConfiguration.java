package com.test.weather.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The OpenAPIConfiguration class sets up OpenAPI documentation for a Weather Forecast service with
 * server URL specified.
 * 
 * @author N Javeed
 */
@OpenAPIDefinition(
        servers = {
                @Server(url = "/weather-forecast", description = "Weather Forecast Server URL")
        }
)
@Configuration
public class OpenAPIConfiguration {

    /**
     * The function creates and returns an OpenAPI object for a Weather Forecast service with specific
     * information.
     * 
     * @return An OpenAPI object is being returned. It contains information about a Weather Forecast
     * service with details such as title, description, version, and contact information for "Javeed
     * Inc" with the email "meetjaveed11@gmail.com".
     */
    @Bean
    public OpenAPI parsingOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Weather Forecast")
                        .description("Weather Forecast Service")
                        .version("v1.0.0")
                        .contact(
                                new Contact().name("Javeed Inc")
                                        .email("meetjaveed11@gmail.com")));
    }

}
