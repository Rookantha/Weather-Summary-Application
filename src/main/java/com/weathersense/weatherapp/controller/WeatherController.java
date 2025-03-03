package com.weathersense.weatherapp.controller;

import com.weathersense.weatherapp.model.WeatherSummary;
import com.weathersense.weatherapp.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
@Tag(name = "Weather API", description = "API for retrieving weather information")
@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    @Operation(summary = "Get Weather Summary",
            description = "Fetches the current weather summary for a given city")
    public CompletableFuture<WeatherSummary> getWeatherSummary(@RequestParam String city) {
        // Directly return the CompletableFuture without blocking
        return weatherService.getWeatherSummary(city);
    }

}
