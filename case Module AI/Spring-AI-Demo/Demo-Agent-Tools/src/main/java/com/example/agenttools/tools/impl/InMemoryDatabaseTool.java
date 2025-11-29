package com.example.agenttools.tools.impl;

import com.example.agenttools.core.DatabaseTool;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class InMemoryDatabaseTool implements DatabaseTool {
    public record WeatherRecord(String city, String info) {}

    private final List<WeatherRecord> records = new CopyOnWriteArrayList<>();

    @Override
    public void saveWeather(String city, String weatherInfo) {
        records.add(new WeatherRecord(city, weatherInfo));
    }

    public List<WeatherRecord> findAll() {
        return new ArrayList<>(records);
    }
}

