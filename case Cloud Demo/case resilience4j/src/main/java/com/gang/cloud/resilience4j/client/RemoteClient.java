package com.gang.cloud.resilience4j.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname RemoteClient
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
@Component
public class RemoteClient {

    @Autowired
    private RestTemplate restTemplate;


    public String test() {
        return restTemplate.getForObject("127.0.0.1:8089/test/get", String.class);
    }

}
