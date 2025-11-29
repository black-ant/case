package com.example.agenttools.tools.impl;

import com.example.agenttools.core.WeatherTool;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StubWeatherTool implements WeatherTool {
    @Override
    public String queryWeather(String city) {
        // Static stub for demo purposes
        return city + "：晴，20℃，微风，" + LocalDate.now();
    }
}

