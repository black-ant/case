package com.gang.oauth.demo.service;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname WorkWechatService
 * @Description TODO
 * @Date 2021/1/26 22:24
 * @Created by zengzg
 */
@Service
public class WorkWechatService implements ApplicationRunner {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    OkHttpClient client = new OkHttpClient();

    public static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken" +
            "?corpid=corpidValue&corpsecret=corpsecretValue";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in DingTlkService <-------");
        getAccessToken();
    }


    /**
     * Step 1 : corpidValue -> 我的企业中 ww 开头
     * Step 2 : corpsecretValue -> 对应应用得 Secret
     */
    public void getAccessToken() throws Exception {


        String gettokenurl = ACCESS_TOKEN_URL
                .replace("corpidValue", "企业ID")
                .replace("corpsecretValue", "123123123");

        System.out.println(gettokenurl);
        Request request = new Request.Builder()
                .url(gettokenurl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            logger.info("------> this is response :{} <-------", response.body().string());
        }

    }


}
