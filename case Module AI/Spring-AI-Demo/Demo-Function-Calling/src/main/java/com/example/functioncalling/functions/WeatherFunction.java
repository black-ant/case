package com.example.functioncalling.functions;

import com.example.functioncalling.model.WeatherRequest;
import com.example.functioncalling.model.WeatherResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.function.Function;

/**
 * 天气查询函数
 * 实现 Function 接口，Spring AI 会自动识别并注册
 */
@Slf4j
public class WeatherFunction implements Function<WeatherRequest, WeatherResponse> {
    
    private final Random random = new Random();
    
    @Override
    public WeatherResponse apply(WeatherRequest request) {
        log.info("调用天气查询函数: city={}, unit={}", request.getCity(), request.getUnit());
        
        // 模拟天气数据（实际应用中应该调用真实的天气 API）
        double temperature = 15 + random.nextInt(20);
        if ("fahrenheit".equalsIgnoreCase(request.getUnit())) {
            temperature = temperature * 9 / 5 + 32;
        }
        
        String[] conditions = {"晴天", "多云", "阴天", "小雨", "大雨"};
        String condition = conditions[random.nextInt(conditions.length)];
        
        int humidity = 40 + random.nextInt(40);
        
        String description = String.format("%s今天%s，温度%.1f%s，湿度%d%%",
                request.getCity(),
                condition,
                temperature,
                "celsius".equalsIgnoreCase(request.getUnit()) ? "℃" : "℉",
                humidity);
        
        return new WeatherResponse(
                request.getCity(),
                temperature,
                request.getUnit(),
                condition,
                humidity,
                description
        );
    }
}
