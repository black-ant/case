package com.gang.study.resttemplate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Classname WebInterceptor
 * @Description TODO
 * @Date 2019/12/17 15:27
 * @Created by zengzg
 */
@Component
public class WebInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {


        HttpHeaders headers = httpRequest.getHeaders();
        headers.add("test", "gang");

        // 保证请求继续被执行
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
