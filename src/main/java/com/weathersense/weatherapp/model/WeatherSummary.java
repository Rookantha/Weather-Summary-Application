package com.weathersense.weatherapp.model;

public class WeatherSummary {

        private String city;
        private double averageTemperature;
        private String hottestDay;
        private String coldestDay;

        // No-argument constructor
        public WeatherSummary() {
        }

        // Constructor with arguments
        public WeatherSummary(String city, double averageTemperature, String hottestDay, String coldestDay) {
                this.city = city;
                this.averageTemperature = averageTemperature;
                this.hottestDay = hottestDay;
                this.coldestDay = coldestDay;
        }

        // Getters and setters
        public String getCity() {
                return city == null ? null : city.trim();
        }

        public void setCity(String city) {
                this.city = city == null ? null : city.trim();
        }

        public double getAverageTemperature() {
                return averageTemperature;
        }

        public void setAverageTemperature(double averageTemperature) {
                this.averageTemperature = averageTemperature;
        }

        public String getHottestDay() {
                return hottestDay;
        }

        public void setHottestDay(String hottestDay) {
                this.hottestDay = hottestDay;
        }

        public String getColdestDay() {
                return coldestDay;
        }

        public void setColdestDay(String coldestDay) {
                this.coldestDay = coldestDay;
        }
}
