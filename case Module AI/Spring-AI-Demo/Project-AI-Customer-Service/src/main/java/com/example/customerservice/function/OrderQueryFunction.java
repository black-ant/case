package com.example.customerservice.function;

import com.example.customerservice.entity.Order;
import com.example.customerservice.repository.OrderRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.function.Function;

/**
 * 订单查询工具
 */
public class OrderQueryFunction implements Function<OrderQueryFunction.Request, OrderQueryFunction.Response> {
    
    private final OrderRepository orderRepository;
    
    public OrderQueryFunction(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @Override
    public Response apply(Request request) {
        if (request.orderNumber != null) {
            return orderRepository.findByOrderNumber(request.orderNumber)
                .map(order -> new Response(true, "订单查询成功", List.of(order), null))
                .orElse(new Response(false, "未找到订单号: " + request.orderNumber, null, null));
        }
        
        if (request.customerId != null) {
            List<Order> orders = orderRepository.findByCustomerId(request.customerId);
            return new Response(true, "查询到 " + orders.size() + " 个订单", orders, null);
        }
        
        return new Response(false, "请提供订单号或客户ID", null, null);
    }
    
    public record Request(
        @JsonProperty(value = "orderNumber", required = false) String orderNumber,
        @JsonProperty(value = "customerId", required = false) String customerId
    ) {}
    
    public record Response(
        boolean success,
        String message,
        List<Order> orders,
        String error
    ) {}
}
