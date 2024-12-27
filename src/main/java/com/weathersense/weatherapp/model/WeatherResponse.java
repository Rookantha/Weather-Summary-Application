package com.weathersense.weatherapp.model;

import java.util.List;

public class WeatherResponse {

    private List<Forecast> list;

    // Getter method for list
    public List<Forecast> getList() {
        return list;
    }

    // Setter method for list
    public void setList(List<Forecast> list) {
        this.list = list;
    }

    // Define the Forecast class and other necessary fields
    public static class Forecast {
        private Main main;
        private String dt_txt;

        // No-argument constructor for deserialization
        public Forecast() {
        }

        // Constructor for test purposes
        public Forecast(String dt_txt, Main main) {
            this.dt_txt = dt_txt;
            this.main = main;
        }

        public Main getMain() {
            return main;
        }

        public void setMain(Main main) {
            this.main = main;
        }

        public String getDt_txt() {
            return dt_txt;
        }

        public void setDt_txt(String dt_txt) {
            this.dt_txt = dt_txt;
        }
    }

    // Main class with no-argument constructor added
    public static class Main {
        private double temp;

        // No-argument constructor for deserialization
        public Main() {
        }

        // Constructor for test purposes
        public Main(double temp) {
            this.temp = temp;
        }

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }
}
