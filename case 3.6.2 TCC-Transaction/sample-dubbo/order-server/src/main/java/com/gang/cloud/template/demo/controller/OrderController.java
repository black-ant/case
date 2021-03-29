package com.gang.cloud.template.demo.controller;

import com.gang.cloud.template.demo.entity.OrderEntity;
import com.gang.cloud.template.demo.repository.OrderRepository;
import com.gang.cloud.template.demo.service.BuyProductService;
import com.gang.cloud.template.demo.service.OrderService;
import com.gang.cloud.template.to.CommonOrderTO;
import com.gang.cloud.template.to.CommonProductTO;
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
    private BuyProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping("list")
    public Collection<CommonOrderTO> list() {
        return orderService.list();
    }

    @GetMapping("get/{id}")
    public CommonOrderTO list(@PathVariable("id") Integer id) {
        return orderService.getOne(id);
    }

    @GetMapping("buy")
    public String buy() {
        return productService.buySingleProduct("1", 1);
    }

}
