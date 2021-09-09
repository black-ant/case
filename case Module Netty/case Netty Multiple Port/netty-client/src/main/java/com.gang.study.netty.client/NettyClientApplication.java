package com.gang.study.netty.client;

import com.gang.study.netty.client.logic.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Classname NettyClientApplication
 * @Description TODO
 * @Date 2019/12/14 23:02
 * @Created by zengzg
 */
@SpringBootApplication
@EnableScheduling
public class NettyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyClientApplication.class, args);
        //启动netty客户端
        NettyClient nettyClient = new NettyClient();
        nettyClient.start();
    }
}
