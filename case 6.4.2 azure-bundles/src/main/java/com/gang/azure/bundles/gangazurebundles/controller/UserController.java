package com.gang.azure.bundles.gangazurebundles.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.azure.bundles.gangazurebundles.config.DefaultProperties;
import com.gang.azure.bundles.gangazurebundles.entity.AzureClientTokenTO;
import com.gang.azure.bundles.gangazurebundles.entity.AzureConfigTO;
import com.gang.azure.bundles.gangazurebundles.logic.TokenLogic;
import com.gang.azure.bundles.gangazurebundles.utils.HttpClientUtils;
import com.gang.azure.bundles.gangazurebundles.utils.UrlTemplateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2020/3/31 20:44
 * @Created by zengzg
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenLogic tokenLogic;

    @GetMapping("/get")
    public String getUser() {
        AzureConfigTO azureConfigTO = new AzureConfigTO();
        String path = UrlTemplateUtils.getUrl(DefaultProperties.userList,
                (JSONObject) JSONObject.toJSON(azureConfigTO));
        logger.info("------> this is path :{} <-------", path);

        String back = HttpClientUtils.doGet(path, tokenLogic.commonHeader(new AzureClientTokenTO()));
        return back;
    }
}
