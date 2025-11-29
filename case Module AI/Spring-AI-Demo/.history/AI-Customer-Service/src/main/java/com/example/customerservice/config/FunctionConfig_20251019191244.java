package com.example.customerservice.config;

import com.example.customerservice.function.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

/**
 * Function Calling 配置
 */
@Configuration
public class FunctionConfig {
    
    @Bean
    @Description("查询订单信息，可以通过订单号或客户ID查询")
    public OrderQueryFunction orderQueryFunction(
        com.example.customerservice.repository.OrderRepository orderRepository) {
        return new OrderQueryFunction(orderRepository);
    }
    
    @Bean
    @Description("查询物流追踪信息，需要提供订单号")
    public TrackingQueryFunction trackingQueryFunction(
        com.example.customerservice.repository.OrderRepository orderRepository) {
        return new TrackingQueryFunction(orderRepository);
    }
    
    @Bean
    @Description("创建客服工单，用于记录客户问题和投诉")
    public TicketCreateFunction ticketCreateFunction(
        com.example.customerservice.repository.CustomerTicketRepository ticketRepository) {
        return new TicketCreateFunction(ticketRepository);
    }
    
    @Bean
    @Description("查询客户的工单列表")
    public TicketQueryFunction ticketQueryFunction(
        com.example.customerservice.repository.CustomerTicketRepository ticketRepository) {
        return new TicketQueryFunction(ticketRepository);
    }
}
