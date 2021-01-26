package com.gang.oauth.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname DingTalkService
 * @Description TODO
 * @Date 2021/1/26 22:23
 * @Created by zengzg
 */
@Service
public class DingTalkService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/gettoken";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in DingTlkService <-------");
    }

    public void getAccessToken() {

        String appId = "appId";
        String appSecret = "appSecret";

        DefaultDingTalkClient client = new DefaultDingTalkClient(ACCESS_TOKEN_URL);
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appId);
        request.setAppsecret(appSecret);
        request.setHttpMethod("GET");
        try {
            OapiGettokenResponse response = client.execute(request);
            if (0 == response.getErrcode()) {
                logger.info("------> this is dingTalk Response :{} <-------", JSONObject.toJSONString(response));
            } else {
                throw new ApiException("Request Success But Get Wrong Response");
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


}
