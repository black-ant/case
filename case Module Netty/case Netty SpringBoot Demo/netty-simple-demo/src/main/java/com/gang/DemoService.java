package com.gang;

/**
 *
 */
public class DemoService {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start ：开始整个 Netty 搭建流程");

        // S1 : 分别开2个线程模拟 Server 和 Client 的开启
        NettyServer server = new NettyServer();
        server.start();

        NettyClient client = new NettyClient();
        client.start();
    }

}
