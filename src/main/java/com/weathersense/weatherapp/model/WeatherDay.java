package com.weathersense.weatherapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDay {

    private long date; // Timestamp (long)
    private double temperature;

    public WeatherDay(long date, double temperature) {
        this.date = date;
        this.temperature = temperature;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "WeatherDay{date=" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(date)) + ", temperature=" + temperature + "}";
    }
}
