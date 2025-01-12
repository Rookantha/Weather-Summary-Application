package com.weathersense.weatherapp.model;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDay {

    private long date; // Timestamp (long)
    private double temperature;

    @Override
    public String toString() {
        return "WeatherDay{date=" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(date)) + ", temperature=" + temperature + "}";
    }
}
