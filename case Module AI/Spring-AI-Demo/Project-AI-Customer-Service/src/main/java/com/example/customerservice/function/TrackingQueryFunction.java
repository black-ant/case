package com.example.customerservice.function;

import com.example.customerservice.repository.OrderRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.function.Function;

/**
 * 物流追踪查询工具
 */
public class TrackingQueryFunction implements Function<TrackingQueryFunction.Request, TrackingQueryFunction.Response> {
    
    private final OrderRepository orderRepository;
    
    public TrackingQueryFunction(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @Override
    public Response apply(Request request) {
        return orderRepository.findByOrderNumber(request.orderNumber)
            .map(order -> {
                if (order.getTrackingNumber() == null) {
                    return new Response(false, "订单尚未发货", null, null, null);
                }
                return new Response(
                    true,
                    "物流信息查询成功",
                    order.getTrackingNumber(),
                    order.getStatus().name(),
                    order.getShippedDate()
                );
            })
            .orElse(new Response(false, "未找到订单", null, null, null));
    }
    
    public record Request(
        @JsonProperty(value = "orderNumber", required = true) String orderNumber
    ) {}
    
    public record Response(
        boolean success,
        String message,
        String trackingNumber,
        String status,
        Object shippedDate
    ) {}
}
