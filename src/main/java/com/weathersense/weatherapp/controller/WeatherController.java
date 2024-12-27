package com.weathersense.weatherapp.controller;

import com.weathersense.weatherapp.model.WeatherSummary;
import com.weathersense.weatherapp.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public CompletableFuture<WeatherSummary> getWeatherSummary(@RequestParam String city) {
        // Directly return the CompletableFuture without blocking
        return weatherService.getWeatherSummary(city);
    }

}
