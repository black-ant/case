package com.example.functioncalling.functions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.function.Function;

/**
 * 股票价格查询函数
 */
@Slf4j
public class StockPriceFunction implements Function<StockPriceFunction.Request, StockPriceFunction.Response> {
    
    private final Random random = new Random();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        @JsonProperty(required = true)
        @JsonPropertyDescription("股票代码，例如：AAPL（苹果）、GOOGL（谷歌）、MSFT（微软）")
        private String symbol;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String symbol;
        private double price;
        private double change;
        private String changePercent;
        private String description;
    }
    
    @Override
    public Response apply(Request request) {
        log.info("调用股票价格查询函数: symbol={}", request.getSymbol());
        
        // 模拟股票数据
        double basePrice = 100 + random.nextInt(400);
        double change = (random.nextDouble() - 0.5) * 10;
        double changePercent = (change / basePrice) * 100;
        
        String description = String.format("%s 当前价格 $%.2f，%s $%.2f (%.2f%%)",
                request.getSymbol(),
                basePrice,
                change >= 0 ? "上涨" : "下跌",
                Math.abs(change),
                Math.abs(changePercent));
        
        return new Response(
                request.getSymbol(),
                basePrice,
                change,
                String.format("%.2f%%", changePercent),
                description
        );
    }
}
