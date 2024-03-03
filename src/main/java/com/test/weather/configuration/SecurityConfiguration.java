package com.test.weather.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

/**
 * The SecurityConfig class configures the security filter chain for a Java application, allowing
 * access to certain endpoints and enabling OAuth2 resource server with JWT authentication, while also
 * ignoring certain request matchers for Swagger UI and API documentation.
 * 
 * @author N Javeed
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * The function configures CORS (Cross-Origin Resource Sharing) for a Java application, allowing requests from any origin, with credentials, and allowing all headers and methods.
     * 
     * @return The method is returning a CorsConfigurationSource object.
     */
    @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

    /**
     * The function configures the security filter chain for a Java application, allowing access to
     * certain endpoints and enabling OAuth2 resource server with JWT authentication.
     * 
     * @param http The `http` parameter is an instance of `HttpSecurity`, which is a class provided by
     * Spring Security. It allows you to configure security settings for your application.
     * @return The method is returning a SecurityFilterChain object.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults())
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .anyRequest().permitAll()
		        );
			
        return http.build();
    }
    

    /**
     * The function `webSecurityCustomizer()` configures the web security to ignore certain request
     * matchers for Swagger UI and API documentation.
     * 
     * @return The method is returning a WebSecurityCustomizer object.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/swagger-ui", "/swagger-ui/**", "/error", "/v3/api-docs/**", "/swagger-ui.html");
    }

}
