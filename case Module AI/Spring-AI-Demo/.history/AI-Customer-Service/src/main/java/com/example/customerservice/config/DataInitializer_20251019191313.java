package com.example.customerservice.config;

import com.example.customerservice.entity.Order;
import com.example.customerservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 初始化测试数据
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final OrderRepository orderRepository;
    
    @Override
    public void run(String... args) {
        log.info("初始化测试数据...");
        
        // 创建测试订单
        Order order1 = new Order();
        order1.setOrderNumber("ORD20251019001");
        order1.setCustomerId("CUST001");
        order1.setCustomerName("张三");
        order1.setProductName("iPhone 15 Pro");
        order1.setQuantity(1);
        order1.setTotalAmount(new BigDecimal("7999.00"));
        order1.setStatus(Order.OrderStatus.SHIPPED);
        order1.setOrderDate(LocalDateTime.now().minusDays(3));
        order1.setShippedDate(LocalDateTime.now().minusDays(1));
        order1.setTrackingNumber("SF1234567890");
        order1.setShippingAddress("北京市朝阳区xxx路xxx号");
        
        Order order2 = new Order();
        order2.setOrderNumber("ORD20251019002");
        order2.setCustomerId("CUST001");
        order2.setCustomerName("张三");
        order2.setProductName("AirPods Pro");
        order2.setQuantity(1);
        order2.setTotalAmount(new BigDecimal("1999.00"));
        order2.setStatus(Order.OrderStatus.DELIVERED);
        order2.setOrderDate(LocalDateTime.now().minusDays(10));
        order2.setShippedDate(LocalDateTime.now().minusDays(8));
        order2.setDeliveredDate(LocalDateTime.now().minusDays(5));
        order2.setTrackingNumber("SF0987654321");
        order2.setShippingAddress("北京市朝阳区xxx路xxx号");
        
        Order order3 = new Order();
        order3.setOrderNumber("ORD20251019003");
        order3.setCustomerId("CUST002");
        order3.setCustomerName("李四");
        order3.setProductName("MacBook Pro");
        order3.setQuantity(1);
        order3.setTotalAmount(new BigDecimal("15999.00"));
        order3.setStatus(Order.OrderStatus.CONFIRMED);
        order3.setOrderDate(LocalDateTime.now().minusHours(2));
        order3.setShippingAddress("上海市浦东新区xxx路xxx号");
        
        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);
        
        log.info("测试数据初始化完成");
    }
}
