package com.gang.cloud.template.demo.client;

import com.gang.cloud.template.demo.entity.CloudTemplateEntity;
import com.gang.cloud.template.to.CommonProductTO;
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
@FeignClient("product-server")
@Component
public interface ProductFeignClient {

    @GetMapping("/template/get")
    CloudTemplateEntity get(@RequestParam("desc") String desc);

    @GetMapping("/product/list")
    Collection<CommonProductTO> list();

    @GetMapping("/product/get/{id}")
    CommonProductTO getById(@PathVariable("id") String id);

    @GetMapping("/product/buy/{orderId}")
    String buyProduct(@PathVariable("orderId") String orderId);

}
