package com.example.functioncalling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    private String city;
    private double temperature;
    private String unit;
    private String condition;
    private int humidity;
    private String description;
}
