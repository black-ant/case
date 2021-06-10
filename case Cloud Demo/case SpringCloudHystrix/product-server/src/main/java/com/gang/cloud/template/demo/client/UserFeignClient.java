package com.gang.cloud.template.demo.client;

import com.gang.cloud.template.demo.entity.CloudTemplateEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Classname SampleFeignClient
 * @Description TODO
 * @Created by zengzg
 */
@FeignClient("product-server")
@Component
public interface UserFeignClient {

    @GetMapping("/template/get")
    CloudTemplateEntity get(@RequestParam("desc") String desc);

}
