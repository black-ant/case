package com.gang.study.cloud.nacos.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Classname ConfigController
 * @Description TODO
 * @Date 2020/11/3 23:34
 * @Created by zengzg
 */
@RestController
@RequestMapping("config")
public class ConfigController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;

    @Autowired
    public RestTemplate restTemplate;

    @GetMapping("test")
    public String test() {
        logger.info("------> test <-------");
        return "ok";
    }

    @GetMapping(value = "/get")
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }

    /**
     * http://127.0.0.1:8086/config/set?key=1&value=2
     *
     * @param key
     * @param value
     */
    @GetMapping(value = "/set")
    public void setValue(@RequestParam("key") String key, @RequestParam("value") String value) {

        try {
            String url = "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=%s&group=DEFAULT_GROUP&content=%s";
            ResponseEntity<String> response = restTemplate.postForEntity(String.format(url, key, value), null,
                    String.class);

            logger.info("------> response :{} <-------", response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping(value = "/setExample")
    public void setExample(@RequestParam("key") String key, @RequestParam("value") String value) {

        try {
            // 此方式生成的值会整个覆盖
            String url = "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=example&group=DEFAULT_GROUP&content=%s=%s";
            ResponseEntity<String> response = restTemplate.postForEntity(String.format(url, key, value), null,
                    String.class);

            logger.info("------> response :{} <-------", response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
