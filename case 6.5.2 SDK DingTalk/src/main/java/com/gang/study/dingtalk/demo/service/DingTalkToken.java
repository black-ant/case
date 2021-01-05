package com.gang.study.dingtalk.demo.service;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSsoGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSsoGettokenResponse;
import com.gang.study.dingtalk.demo.type.DingTalkAPIEnum;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @Classname DingTalkToken
 * @Description TODO
 * @Date 2020/1/19 10:56
 * @Created by zengzg
 */
@Component
public class DingTalkToken {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${ding.appkey}")
    public String appkey;

    @Value("${ding.appsecret}")
    public String appsecret;

    // Test Toke
    public static String DING_TOKEN = null;

    static {
        DING_TOKEN = "48eccb33c53332319cf9e9e6f9523c6b";
    }

    public String getToken() {
        logger.info("------> 获取应用 token  <-------");
        if (DING_TOKEN == null) {
            try {
                DING_TOKEN = getTokenLogic();
            } catch (ApiException e) {
                logger.error("------> get DingToken error <-------");
            }
        }
        return DING_TOKEN;
    }

    public String getTokenLogic() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient(DingTalkAPIEnum.TOKEN_API.getUrl());
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appkey);
        request.setAppsecret(appsecret);
        request.setHttpMethod("GET");

        logger.info("------> getToken get Response <-------");

        OapiGettokenResponse response = client.execute(request);
        if (null != response && response.isSuccess()) {
            logger.info("------> getToken response ok :{} <-------", response.getAccessToken());
            return response.getAccessToken();
        } else {
            logger.info("------> DingTalk getToken no Response :{} <-------", response);
            return "";
        }
    }
}
