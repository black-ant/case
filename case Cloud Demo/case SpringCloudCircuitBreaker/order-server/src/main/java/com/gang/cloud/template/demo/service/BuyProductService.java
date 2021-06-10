package com.gang.cloud.template.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @Classname BuySingleProduct
 * @Description TODO
 * @Created by zengzg
 */
@Service
public class BuyProductService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private RestTemplate rest;

    public BuyProductService(RestTemplate rest) {
        this.rest = rest;
    }

    public Map get() {
        return rest.getForObject("http://httpbin.org/get", Map.class);

    }

    public String buySingleProduct(String accountId, String productId) {


        return "";
    }

    public Map delay(int seconds) {
        return rest.getForObject("http://httpbin.org/delay/" + seconds, Map.class);
    }

    public Supplier<Map> delaySuppplier(int seconds) {
        return () -> this.delay(seconds);
    }
}
