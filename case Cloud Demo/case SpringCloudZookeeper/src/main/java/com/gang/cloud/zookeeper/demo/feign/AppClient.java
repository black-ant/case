package com.gang.cloud.zookeeper.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Classname AppClient
 * @Description TODO
 * @Date 2021/8/2
 * @Created by zengzg
 */
@FeignClient("testZookeeperApp")
public interface AppClient {

    @RequestMapping(path = "/hi", method = RequestMethod.GET)
    String hi();

}
