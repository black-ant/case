package com.example.functioncalling.functions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * 计算器函数
 */
@Slf4j
public class CalculatorFunction implements Function<CalculatorFunction.Request, CalculatorFunction.Response> {
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @JsonProperty(required = true)
        @JsonPropertyDescription("第一个数字")
        private double num1;
        
        @JsonProperty(required = true)
        @JsonPropertyDescription("第二个数字")
        private double num2;
        
        @JsonProperty(required = true)
        @JsonPropertyDescription("运算符：add（加）、subtract（减）、multiply（乘）、divide（除）")
        private String operation;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private double result;
        private String expression;
        private String description;
    }
    
    @Override
    public Response apply(Request request) {
        log.info("调用计算器函数: {} {} {}", request.getNum1(), request.getOperation(), request.getNum2());
        
        double result;
        String operator;
        
        switch (request.getOperation().toLowerCase()) {
            case "add":
                result = request.getNum1() + request.getNum2();
                operator = "+";
                break;
            case "subtract":
                result = request.getNum1() - request.getNum2();
                operator = "-";
                break;
            case "multiply":
                result = request.getNum1() * request.getNum2();
                operator = "×";
                break;
            case "divide":
                if (request.getNum2() == 0) {
                    return new Response(0, "", "错误：除数不能为零");
                }
                result = request.getNum1() / request.getNum2();
                operator = "÷";
                break;
            default:
                return new Response(0, "", "错误：不支持的运算符");
        }
        
        String expression = String.format("%.2f %s %.2f = %.2f", 
                request.getNum1(), operator, request.getNum2(), result);
        
        return new Response(result, expression, "计算完成：" + expression);
    }
}
