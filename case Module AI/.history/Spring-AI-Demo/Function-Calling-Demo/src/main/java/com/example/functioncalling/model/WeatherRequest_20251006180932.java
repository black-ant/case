package com.example.functioncalling.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

/**
 * 天气查询请求参数
 * 这个类会被 AI 自动识别并调用
 */
@Data
public class WeatherRequest {
    
    @JsonProperty(required = true)
    @JsonPropertyDescription("城市名称，例如：北京、上海、深圳")
    private String city;
    
    @JsonProperty
    @JsonPropertyDescription("温度单位，可选值：celsius（摄氏度）或 fahrenheit（华氏度），默认为 celsius")
    private String unit = "celsius";
}
