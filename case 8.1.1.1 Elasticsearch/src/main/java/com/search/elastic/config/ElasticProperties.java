package com.search.elastic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname ElasticProperties
 * @Description TODO
 * @Date 2021/7/15
 * @Created by zengzg
 */
@Configuration
public class ElasticProperties {

    @Value("${remoteHost:127.0.0.1}")
    private String host;

    @Value("${remotePort:9300}")
    private String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getRemoteUrl() {
        return host + ":" + port;
    }
}
