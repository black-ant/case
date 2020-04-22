package com.gang.study.feign.common.demo.mapper;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Classname ServiceMapper
 * @Description TODO
 * @Date 2020/4/22 21:03
 * @Created by zengzg
 */
@FeignClient(name = "CLOUD-SERVICE", path = "/cloud-service")
public interface ServiceMapper {

    @GetMapping("/user/get/{key}")
    JSONObject get(@PathVariable("key") String key);

}
