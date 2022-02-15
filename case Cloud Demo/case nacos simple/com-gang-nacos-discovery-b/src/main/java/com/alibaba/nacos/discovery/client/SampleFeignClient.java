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
@FeignClient("nacos-user-server")
@Component
public interface SampleFeignClient {

    @GetMapping("/config/get")
    String get(@RequestParam("dataId") String dataId, @RequestParam("groupId") String groupId);


    @GetMapping("/discovery/user")
    String getUser();

}
