package com.gang.nacos.config.client;

import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Classname SampleFeignClient
 * @Description TODO
 * @Date 2021/3/4 21:21
 * @Created by zengzg
 */
@FeignClient("nacos-user-server")
@Component
public interface SampleFeignClient {

    @GetMapping("/discovery/get")
    List<Instance> get(@RequestParam("serviceName") String serviceName);


}
