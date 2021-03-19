package com.gang.cloud.template.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.demo.client.OrderFeignClient;
import com.gang.cloud.template.demo.entity.ProductEntity;
import com.gang.cloud.template.demo.repository.ProductRepository;
import com.gang.cloud.template.to.CommonOrderTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @Classname AccountController
 * @Description TODO
 * @Created by zengzg
 */
@RequestMapping("/product")
@RestController
public class ProductController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderFeignClient orderFeignClient;

    @GetMapping("list")
    public Collection<ProductEntity> list() {
        return productRepository.findAll();
    }

    @GetMapping("get/{id}")
    public ProductEntity list(@PathVariable("id") String id) {
        return productRepository.getOne(id);
    }

    @GetMapping("buy/{productId}")
    public String buyProduct(@PathVariable("productId") String orderId) {
        CommonOrderTO CommonOrderTO = orderFeignClient.getById(orderId);
        logger.info("------> ProductController : 订单查询成功 :{} <-------", JSONObject.toJSONString(CommonOrderTO));
        return "success";
    }

}
