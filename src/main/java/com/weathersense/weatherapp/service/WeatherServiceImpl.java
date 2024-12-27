package com.weathersense.weatherapp.service;

import com.weathersense.weatherapp.config.WeatherApiConfig;
import com.weathersense.weatherapp.model.WeatherResponse;
import com.weathersense.weatherapp.model.WeatherSummary;
import com.weathersense.weatherapp.util.WeatherDataProcessor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@EnableAsync
public class WeatherServiceImpl implements WeatherService {

    private final WebClient webClient;
    private final String apiKey;
    private final String apiUrl;

    public WeatherServiceImpl(WebClient.Builder webClientBuilder, WeatherApiConfig weatherApiConfig) {
        this.webClient = webClientBuilder.baseUrl(weatherApiConfig.getUrl()).build();
        this.apiKey = weatherApiConfig.getKey();
        this.apiUrl = weatherApiConfig.getUrl();
    }

    @Override
    @Cacheable(value = "weatherSummary", key = "#city", unless = "#result == null", cacheManager = "cacheManager")
    @Async
    public CompletableFuture<WeatherSummary> getWeatherSummary(String city) {
        // Fetch data from the weather API
        WeatherResponse weatherResponse = fetchWeatherData(city);

        // Process the weather data
        WeatherSummary weatherSummary = processWeatherData(weatherResponse, city);

        return CompletableFuture.completedFuture(weatherSummary);
    }

    private WeatherResponse fetchWeatherData(String city) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("q", city).queryParam("appid", apiKey).build())
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .block();
    }

    private WeatherSummary processWeatherData(WeatherResponse weatherResponse, String city) {
        List<WeatherResponse.Forecast> forecasts = weatherResponse.getList();

        // Group forecasts by date and calculate daily averages
        Map<LocalDate, List<Double>> dailyTemperatures = forecasts.stream()
                .collect(Collectors.groupingBy(
                        forecast -> LocalDate.parse(forecast.getDt_txt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        Collectors.mapping(forecast -> forecast.getMain().getTemp() - 273.15, Collectors.toList())
                ));

        // Calculate average temperature for each day
        Map<LocalDate, Double> dailyAverages = dailyTemperatures.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0)));

        LocalDate hottestDay = Collections.max(dailyAverages.entrySet(), Map.Entry.comparingByValue()).getKey();
        LocalDate coldestDay = Collections.min(dailyAverages.entrySet(), Map.Entry.comparingByValue()).getKey();
        double avgTemp = dailyAverages.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);

        // Create WeatherSummary object
        WeatherSummary summary = new WeatherSummary();
        summary.setCity(city);
        summary.setAverageTemperature(avgTemp);
        summary.setHottestDay(hottestDay.toString());
        summary.setColdestDay(coldestDay.toString());

        return summary;
    }
}
