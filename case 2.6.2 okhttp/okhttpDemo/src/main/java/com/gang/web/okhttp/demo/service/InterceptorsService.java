package com.gang.web.okhttp.demo.service;

import okhttp3.Interceptor;
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
 * @Classname InterceptorsService
 * @Description TODO
 * @Date 2021/1/10 17:02
 * @Created by zengzg
 */
@Service
public class InterceptorsService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .build();

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        doGet();
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

    }

    class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            logger.info(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            logger.info(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}
