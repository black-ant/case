package com.gang.cloud.template.demo.client;

import com.gang.cloud.template.demo.config.ProductServiceFallBack;
import com.gang.cloud.template.to.CommonProductTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

/**
 * @Classname SampleFeignClient
 * @Description TODO
 * @Date 2021/3/4 21:21
 * @Created by zengzg
 */
@FeignClient(value = "product-server", fallback = ProductServiceFallBack.class)
@Component
public interface ProductFeignClient {

    @GetMapping("/product/list")
    Collection<CommonProductTO> list();

    @GetMapping("/product/get/{id}")
    CommonProductTO getById(@PathVariable("id") String id);

    @GetMapping("/product/buy/{orderId}")
    String buyProduct(@PathVariable("orderId") String orderId);

    @GetMapping("/product/test")
    String test();

    @GetMapping("/product/test002")
    String test002();

}
