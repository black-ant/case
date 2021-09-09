package com.gang.cloud.template.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.demo.client.OrderFeignClient;
import com.gang.cloud.template.demo.entity.NoDataProduct;
import com.gang.cloud.template.to.CommonOrderTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

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
    private OrderFeignClient orderFeignClient;

    @GetMapping("list")
    public Collection<NoDataProduct> list() {
        List<NoDataProduct> list = new LinkedList<>();
        list.add(new NoDataProduct("123"));
        return list;
    }

    @GetMapping("get/{id}")
    public NoDataProduct list(@PathVariable("id") String id) {
        return new NoDataProduct("123");
    }

    @GetMapping("buy/{productId}")
    public String buyProduct(@PathVariable("productId") String orderId) {
        CommonOrderTO CommonOrderTO = orderFeignClient.getById(orderId);
        logger.info("------> ProductController : 订单查询成功 :{} <-------", JSONObject.toJSONString(CommonOrderTO));
        return "success";
    }

    @GetMapping("test")
    public String test() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @GetMapping("test002")
    @HystrixCommand(
            fallbackMethod = "hystrix_fallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")
            })
    public String test002() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }


}
