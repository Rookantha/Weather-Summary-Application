package com.weathersense.weatherapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "WeatherSense API",
        version = "1.0",
        description = "API for retrieving weather data, forecasts, and conditions."
))
public class OpenAPIConfig {

}
