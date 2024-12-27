package com.weathersense.weatherapp.service;

import com.weathersense.weatherapp.model.WeatherSummary;

import java.util.concurrent.CompletableFuture;

public interface WeatherService {
    CompletableFuture<WeatherSummary> getWeatherSummary(String city);
}
