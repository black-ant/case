package com.gang.azure.oauth.gangazureoauth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gang.azure.oauth.gangazureoauth.entity.AdminconsentConfig;
import com.gang.azure.oauth.gangazureoauth.entity.ClientSecretConfig;
import com.gang.azure.oauth.gangazureoauth.entity.ConfigEntity;
import com.gang.azure.oauth.gangazureoauth.utils.HttpClientUtils;
import com.gang.azure.oauth.gangazureoauth.utils.HttpKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Classname CodeOauthController
 * @Description TODO
 * @Date 2020/3/31 11:37
 * @Created by zengzg
 */
@RestController
@RequestMapping("/code")
public class CodeOauthController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/getCode")
    public String backCode() {
        ConfigEntity configEntity = new ConfigEntity();
        List<Field> fieldList = Arrays.asList(configEntity.getClass().getFields());
        StringBuilder searchInfo = new StringBuilder();
        fieldList.forEach(item -> {
            try {
                searchInfo.append("&").append(item.getName()).append("=").append(item.get(configEntity));
            } catch (Exception e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            }
        });

        // 代表用户登录
//        String oauthUrl = "https://login.microsoftonline.com/303370752qq.onmicrosoft.com/oauth2/v2.0/authorize?"
//                + searchInfo.substring(1);

        String oauthUrl = "https://login.microsoftonline.com/303370752qq.onmicrosoft.com/oauth2/v2.0/authorize?"
                + searchInfo.substring(1);
        //无用户登录
        //        String oauthUrl = "https://login.microsoftonline.com/303370752qq.onmicrosoft.com/adminconsent?"
        //                + searchInfo.substring(1);
        String backInfo = HttpClientUtils.doGet(oauthUrl, new HashMap<>());


        logger.info("------> back is :{} <-------", backInfo);
        return backInfo;
    }

    @GetMapping("/getCodeS")
    public String clientSecret() {
        String oauthUrl = "https://login.microsoftonline.com/184e58f8-7a52-421e-801d-4947bb4611a8/oauth2/token";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/x-www-form-urlencoded");

        HashMap<String, String> contentMap = new HashMap();
        contentMap.put("client_id", "bdfcdc00-0177-4499-bd8d-98984d2506f7");
        contentMap.put("grant_type", "client_credentials");
        contentMap.put("resource", "https://303370752qq.onmicrosoft.com/adal4jsample");
        contentMap.put("client_secret", "Cd_=2u9V2zV5/iQ?[bepTN57ze=hd[]?");

        String backInfo = null;
        try {
            backInfo = HttpClientUtils.doPost(oauthUrl, contentMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return backInfo;

    }

}
