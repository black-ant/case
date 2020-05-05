package com.gang.study.oauth.yunzhijia.demo.logic;

import com.alibaba.fastjson.JSONObject;
import com.gang.common.lib.type.DefaultProperties;
import com.gang.common.lib.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname OAuthLogic
 * @Description TODO
 * @Date 2020/4/28 16:12
 * @Created by zengzg
 */
@Component
public class OAuthLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String TOKEN_URL = "https://www.yunzhijia.com/gateway/oauth2/token/getAccessToken";

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

    public void getToken() {
        logger.info("------> this get Token <-------");
        JSONObject requestBody = new JSONObject();
        requestBody.put("appId", "");
        requestBody.put("secret", "");
        requestBody.put("timestamp", "");
        requestBody.put("scope", "");

        String response = HttpClientUtils.doHttpHandle(
                TOKEN_URL,
                DefaultProperties.HTTP_POST,
                HttpClientUtils.helperHeader(DefaultProperties.HTTP_CONTENT_JSON),
                null,
                requestBody);

        logger.info("------> response is :{} <-------", response);

    }
}
