package com.alibaba.nacos.discovery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Classname SampleFeignClient
 * @Description TODO
 * @Date 2021/3/4 21:21
 * @Created by zengzg
 */
@FeignClient("nacos-account-server")
@Component
public interface SampleFeignClient {

//    @GetMapping("/template/get")
//    CloudTemplateEntity get(@RequestParam("desc") String desc);


}
