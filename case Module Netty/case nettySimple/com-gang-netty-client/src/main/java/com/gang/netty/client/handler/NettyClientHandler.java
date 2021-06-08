package com.gang.netty.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Classname NettyClientHandler
 * @Description TODO
 * @Date 2021/6/8
 * @Created by zengzg
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端Active .....");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("客户端收到消息: {}", msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
