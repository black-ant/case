package com.gang.netty.client;

import com.gang.netty.client.service.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Classname ClientApplication
 * @Description TODO
 * @Date 2021/6/8
 * @Created by zengzg
 */
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        //启动netty客户端
        NettyClient nettyClient = new NettyClient();
        nettyClient.start();
    }
}
