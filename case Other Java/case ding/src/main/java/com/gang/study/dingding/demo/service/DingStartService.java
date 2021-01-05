package com.gang.study.dingding.demo.service;

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
 * @Classname DingStartService
 * @Description TODO
 * @Date 2019/11/14 10:45
 * @Created by zengzg
 */
@Service
public class DingStartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    String appkey = "dingfhfkzfvjbilg80qb";
//    String appsecret = "9fzaihJrfQ2YXEUDJPBGTiW4j2toGkRSpVrGPCs44PE_17NnO-GKnQ1LmoB8N46K";

    String appkey = "dingoai3qlwyu6820y1kyx";
    String appsecret = "YowQUO__xK1Tz3OWqwuZOigWJJGPThjDVdqHBxDPgvB_3WeVvgb5YFd6djIjPkvb";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getToken();
    }

    public void getToken() throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(appkey);
        request.setAppsecret(appsecret);
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        logger.info("------> this is  :{} <-------", JSONObject.toJSONString(response));
    }
}
