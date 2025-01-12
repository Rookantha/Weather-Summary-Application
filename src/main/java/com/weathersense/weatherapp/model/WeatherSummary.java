package com.weathersense.weatherapp.model;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherSummary {

        private String city;
        private double averageTemperature;
        private String hottestDay;
        private String coldestDay;

        public void setCity(String city) {
                this.city = city == null ? null : city.trim();
        }

        public String getCity() {
                return city == null ? null : city.trim();
        }
}
