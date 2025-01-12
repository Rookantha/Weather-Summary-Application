package com.weathersense.weatherapp.model;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {

    private List<Forecast> list;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Forecast {
        private Main main;
        private String dt_txt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Main {
        private double temp;
    }
}
