package com.gang.web.okhttp.demo.service;

import com.alibaba.fastjson.JSONObject;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Classname BasicService
 * @Description TODO
 * @Date 2021/1/10 16:17
 * @Created by zengzg
 */
@Service
public class BasicService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in basic Service <-------");
//        doGet();
//        doPost();
    }


    public void doGet() throws Exception {

        // Test
        String url = "http://127.0.0.1:8089/user/get/test";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            logger.info("------> this is response :{} <-------", response.body().string());
        }

        // Get Path
        url = "http://127.0.0.1:8089/user/get/path/key111";
        request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            logger.info("------> this is path response :{} <-------", response.body().string());
        }

        // Get Param
//        HttpUrl httpUrl = new HttpUrl.Builder().scheme("http")
//                .host("127.0.0.1")
//                .port(8089)
//                .addPathSegment("/user/get/param")
//                .addQueryParameter("key", "polar bears")
//                .build();

        HttpUrl httpUrl = HttpUrl.parse("http://127.0.0.1:8089/user/get/param?key=11");
        request = new Request.Builder()
                .url(httpUrl)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            logger.info("------> this is Param response :{} <-------", response.body().string());
        }
    }

    public void doPost() throws Exception {

        String url = "http://127.0.0.1:8089/user/post/create";
        JSONObject json = new JSONObject();
        json.put("username", "ant");
        json.put("age", 18);

        RequestBody body = RequestBody.create(json.toJSONString(), JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            logger.info("------> this is Post response :{} <-------", response.body().string());
        }
    }
}
