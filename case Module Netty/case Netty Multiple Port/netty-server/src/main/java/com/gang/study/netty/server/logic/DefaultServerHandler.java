package com.gang.study.netty.server.logic;

import com.gang.study.netty.server.logic.service.DataTree;
import com.gang.study.netty.server.to.RequestTO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Classname DefaultServerHandler
 * @Description TODO
 * @Date 2021/9/7
 * @Created by zengzg
 */
@Component
public class DefaultServerHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private DataTree dataTree = new DataTree();

    public String addNode(String key, RequestTO value) {
        dataTree.addNode(key, value);
        return "Server Success";
    }

    @Scheduled(cron = "0/5 * *  * * ? ")
    public void test() {
        dataTree.getMap().keySet().forEach(item -> {
            logger.info("------> Key :[{}] > value :[{}]  <-------", item, dataTree.getMap().get(item));
        });
    }


    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("Channel active......");
    }

    /**
     * 客户端发消息会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestTO requestTO = (RequestTO) msg;
        String response = addNode(System.currentTimeMillis() + "-" + new Random().nextInt(999), requestTO);
        ctx.write(response);
        ctx.flush();
    }

    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
