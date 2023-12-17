package com.gang;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @Classname NettyServer
 * @Description TODO
 * @Date 2019/12/14 23:06
 * @Created by zengzg
 */
public class NettyServer extends Thread {

    public void run() {

        // S1 : 准备 Boss 管理线程组 和 Worker 工作线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(200);

        // S2 : 绑定 Group , 绑定渠道 ， 绑定 Handler
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer());

        try {
            InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8090);

            // S3 : 绑定对应端口和地址，用于后续阻塞监听
            ChannelFuture future = bootstrap.bind(socketAddress).sync();
            System.out.println("S1 : 服务端构建完成 .....");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭主线程组
            bossGroup.shutdownGracefully();
            //关闭工作线程组
            workGroup.shutdownGracefully();
        }
    }
}
