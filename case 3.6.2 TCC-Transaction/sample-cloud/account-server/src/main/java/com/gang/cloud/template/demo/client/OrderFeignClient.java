package com.gang.cloud.template.demo.client;

import com.gang.cloud.template.demo.entity.CloudTemplateEntity;
import com.gang.cloud.template.demo.to.OrderTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * @Classname SampleFeignClient
 * @Description TODO
 * @Date 2021/3/4 21:21
 * @Created by zengzg
 */
@FeignClient("order-server")
@Component
public interface OrderFeignClient {

    @GetMapping("/template/get")
    CloudTemplateEntity get(@RequestParam("desc") String desc);


    @GetMapping("/order/list")
    Collection<OrderTO> list();

    @GetMapping("/order/get/{id}")
    OrderTO getById(@PathVariable("id") String id);

}
