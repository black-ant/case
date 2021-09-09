package com.gang.study.netty.client.service;

import com.gang.study.netty.client.logic.NettyClientInitializer;
import com.gang.study.netty.client.to.RequestTO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Classname DataService
 * @Description TODO
 * @Date 2021/9/7
 * @Created by zengzg
 */
@Component
public class DataService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private DataTree dataTree = new DataTree();

    private EventLoopGroup group;
    private Bootstrap bootstrap;

    @Scheduled(cron = "0/5 * *  * * ? ")
    public void show() {
        dataTree.getMap().keySet().forEach(item -> {
            logger.info("------> Key :[{}] > value :[{}]  <-------", item, dataTree.getMap().get(item));
        });
    }

    @Scheduled(cron = "0/10 * *  * * ? ")
    public void create() {
        try {
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8091).sync();
            logger.info("客户端成功....");
            //发送消息
            future.channel().writeAndFlush(buildRequest());
            // 等待连接被关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public RequestTO buildRequest() {
        RequestTO requestTO = new RequestTO();
        requestTO.setUsername("Ant" + new Random().nextInt(99999));
        return requestTO;
    }

    public void buildBootstrap() {
        this.group = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap()
                .group(group)
                //该参数的作用就是禁止使用Nagle算法，使用于小数据即时传输
                .option(ChannelOption.TCP_NODELAY, true)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        buildBootstrap();
    }
}
