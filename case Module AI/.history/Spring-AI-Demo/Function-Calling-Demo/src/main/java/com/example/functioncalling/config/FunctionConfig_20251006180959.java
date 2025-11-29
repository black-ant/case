package com.example.functioncalling.config;

import com.example.functioncalling.functions.CalculatorFunction;
import com.example.functioncalling.functions.StockPriceFunction;
import com.example.functioncalling.functions.WeatherFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * 函数配置类
 * 注册所有可供 AI 调用的函数
 */
@Configuration
public class FunctionConfig {
    
    /**
     * 天气查询函数
     * @Description 注解会被 AI 识别，用于理解函数的用途
     */
    @Bean
    @Description("获取指定城市的天气信息，包括温度、湿度和天气状况")
    public WeatherFunction weatherFunction() {
        return new WeatherFunction();
    }
    
    /**
     * 股票价格查询函数
     */
    @Bean
    @Description("查询指定股票代码的实时价格和涨跌幅信息")
    public StockPriceFunction stockPriceFunction() {
        return new StockPriceFunction();
    }
    
    /**
     * 计算器函数
     */
    @Bean
    @Description("执行基本的数学运算，支持加减乘除")
    public CalculatorFunction calculatorFunction() {
        return new CalculatorFunction();
    }
}
