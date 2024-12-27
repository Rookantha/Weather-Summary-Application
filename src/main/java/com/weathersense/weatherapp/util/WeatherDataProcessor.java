package com.weathersense.weatherapp.util;

import com.weathersense.weatherapp.model.WeatherResponse;
import com.weathersense.weatherapp.model.WeatherSummary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class WeatherDataProcessor {

    private WeatherSummary processWeatherData(WeatherResponse weatherResponse, String city) {
        List<WeatherResponse.Forecast> forecasts = weatherResponse.getList();

        // Get the current date and calculate the date 7 days ago
        LocalDate currentDate = LocalDate.now();
        LocalDate sevenDaysAgo = currentDate.minusDays(7);

        // Filter forecasts to only include the last 7 days
        List<WeatherResponse.Forecast> recentForecasts = forecasts.stream()
                .filter(forecast -> {
                    LocalDate forecastDate = LocalDate.parse(forecast.getDt_txt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    return !forecastDate.isBefore(sevenDaysAgo);
                })
                .collect(Collectors.toList());

        // Group forecasts by date and calculate daily averages for the last 7 days
        Map<LocalDate, List<Double>> dailyTemperatures = recentForecasts.stream()
                .collect(Collectors.groupingBy(
                        forecast -> LocalDate.parse(forecast.getDt_txt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        Collectors.mapping(forecast -> forecast.getMain().getTemp() - 273.15, Collectors.toList())
                ));

        // Calculate average temperature for each day
        Map<LocalDate, Double> dailyAverages = dailyTemperatures.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0)));

        // Calculate the overall average temperature for the last 7 days
        double avgTemp = dailyAverages.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);

        // Find the hottest and coldest day
        LocalDate hottestDay = Collections.max(dailyAverages.entrySet(), Map.Entry.comparingByValue()).getKey();
        LocalDate coldestDay = Collections.min(dailyAverages.entrySet(), Map.Entry.comparingByValue()).getKey();

        // Create WeatherSummary object
        WeatherSummary summary = new WeatherSummary();
        summary.setCity(city);
        summary.setAverageTemperature(avgTemp);
        summary.setHottestDay(hottestDay.toString());
        summary.setColdestDay(coldestDay.toString());

        return summary;
    }

}
