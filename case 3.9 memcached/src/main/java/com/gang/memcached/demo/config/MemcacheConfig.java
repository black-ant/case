package com.gang.memcached.demo.config;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @Classname MemcacheConfig
 * @Description TODO
 * @Date 2022/2/15
 * @Created by zengzg
 * @Created by zengzg
 */
@Configuration
public class MemcacheConfig {

    @Value("${memcache.ip}")
    private String ip;

    @Value("${memcache.port}")
    private int port;

    @Bean
    public MemcachedClient getMemcachedClient() {

        MemcachedClient memcachedClient = null;

        try {
            memcachedClient = new MemcachedClient(new InetSocketAddress(ip, port));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return memcachedClient;
    }
}
