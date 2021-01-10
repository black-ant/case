package com.gang.web.okhttp.demo.service;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Classname FileService
 * @Description TODO
 * @Date 2021/1/10 17:07
 * @Created by zengzg
 */
@Service
public class FileService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OkHttpClient client = new OkHttpClient();


    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in basic Service <-------");
        run();
    }


    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://publicobject.com/helloworld.txt")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            System.out.println(response.body().string());
        }
    }
}
