package com.gang.cloud.template.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.demo.client.OrderFeignClient;
import com.gang.cloud.template.demo.entity.NoDataProduct;
import com.gang.cloud.template.demo.repository.NoDataProductRepository;
import com.gang.cloud.template.demo.to.OrderTO;
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
    private NoDataProductRepository productRepository;
    @Autowired
    private OrderFeignClient orderFeignClient;

    @GetMapping("list")
    public Collection<NoDataProduct> list() {
        return productRepository.getDatabase().values();
    }

    @GetMapping("get/{id}")
    public NoDataProduct list(@PathVariable("id") String id) {
        return productRepository.getProductById(id);
    }

    @GetMapping("buy/{productId}")
    public String buyProduct(@PathVariable("productId") String orderId) {
        OrderTO orderTO = orderFeignClient.getById(orderId);
        logger.info("------> ProductController : 订单查询成功 :{} <-------", JSONObject.toJSONString(orderTO));


        // 更新账户
        NoDataProduct product = productRepository.getProductById(orderTO.getAccountId());
        product.setProductNum(product.getProductNum().subtract(orderTO.getProductNum()));
        productRepository.updateProduct(product);

        return "success";
    }

}
