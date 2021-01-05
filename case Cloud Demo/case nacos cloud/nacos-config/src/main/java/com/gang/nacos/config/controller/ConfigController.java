package com.gang.nacos.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname ConfigController
 * @Description TODO
 * @Date 2020/11/8 11:22
 * @Created by zengzg
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    /**
     * DataId 的格式 : ${prefix}-${spring.profiles.active}.${file-extension}
     *
     */

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }
}
