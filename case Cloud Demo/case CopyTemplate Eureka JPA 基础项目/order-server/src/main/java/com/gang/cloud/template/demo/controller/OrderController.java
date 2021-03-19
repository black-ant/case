package com.gang.cloud.template.demo.controller;

import com.gang.cloud.template.demo.entity.OrderEntity;
import com.gang.cloud.template.demo.repository.OrderRepository;
import com.gang.cloud.template.demo.service.BuyProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @Classname OrderController
 * @Description TODO
 * @Created by zengzg
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BuyProductService productService;

    @GetMapping("list")
    public Collection<OrderEntity> list() {
        return orderRepository.findAll();
    }

    @GetMapping("get/{id}")
    public OrderEntity list(@PathVariable("id") String id) {
        return orderRepository.getOne(id);
    }

    @GetMapping("buy")
    public String buy() {
        return productService.buySingleProduct("1", "1");
    }

}
