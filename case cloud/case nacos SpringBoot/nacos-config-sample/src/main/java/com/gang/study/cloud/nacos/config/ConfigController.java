package com.gang.study.cloud.nacos.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Classname ConfigController
 * @Description TODO
 * @Date 2020/11/3 23:34
 * @Created by zengzg
 */
@Controller
@RequestMapping("config")
public class ConfigController {

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @GetMapping(value = "/get")
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }

    @GetMapping(value = "/set")
    public void setValue(@RequestParam("key") String key, @RequestParam("value") String value) {

    }
}
