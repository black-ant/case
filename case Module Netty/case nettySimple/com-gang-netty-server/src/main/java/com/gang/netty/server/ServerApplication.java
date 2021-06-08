package com.gang.netty.server;

import com.gang.netty.server.service.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

/**
 * @Classname ServerApplication
 * @Description TODO
 * @Date 2021/6/8
 * @Created by zengzg
 */
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        //启动服务端
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(new InetSocketAddress("127.0.0.1", 8090));
    }

}
