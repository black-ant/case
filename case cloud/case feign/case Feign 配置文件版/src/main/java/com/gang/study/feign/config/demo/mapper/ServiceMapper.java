package com.gang.study.feign.config.demo.mapper;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Classname ServiceMapper
 * @Description TODO
 * @Date 2020/4/22 21:03
 * @Created by zengzg
 */
@RequestMapping("/user")
public interface ServiceMapper {

    @GetMapping("/get/{key}")
    JSONObject get(@PathVariable("key") String key);
}
