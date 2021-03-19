package com.gang.license.demo.controller;

import com.gang.license.demo.service.LicenseInfoService;
import com.gang.license.demo.to.LicenseTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname StartController
 * @Description 该方式是基于 @ https://github.com/kobeyk/license 进行的改造 , 可以去上述 git 查看完整方式
 * @Date 2021/3/19
 * @Created by zengzg
 */
@RestController
@RequestMapping("license")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LicenseInfoService licenseInfoService;

    @GetMapping("licenseInfo")
    public String getLicenseInfo() {
        logger.info("------> [获取 license 证书信息 ] <-------");
        return "";
    }

    @GetMapping("clientInfo")
    public LicenseTO clientInfo() throws Exception {
        logger.info("------> [获取 license 证书信息 ] <-------");
        return licenseInfoService.getDefaultServerInfo();
    }
}
