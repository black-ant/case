package com.gang.demo.nio.demo.selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Classname ServerSocketChannel
 * @Description TODO
 * @Date 2021/7/25
 * @Created by zengzg
 */
@Component
public class ServerSocketChannelService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        try {
//            createSocketServer();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 构建一个简单的 SockChannel
     * @throws Exception
     */
    private void createSocketServer() throws Exception {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(9999));

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            //do something with socketChannel...
        }

    }

    /**
     * 常用方法
     * @throws Exception
     */
    private void other() throws Exception {
        // --> 打开 ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();


        // --> 关闭 ServerSocketChannel
        serverSocketChannel.close();

        // --> 监听新进来的连接
        SocketChannel socketChannel = serverSocketChannel.accept();
    }

    /**
     * 非阻塞模式
     * @throws Exception
     */
    private void async() throws Exception {
        // ServerSocketChannel可以
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        // 设置成非阻塞模式
        serverSocketChannel.configureBlocking(false);
        while (true) {
            // 此时accept() 方法会立刻返回，如果还没有新进来的连接,返回的将是null
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                //do something with socketChannel...
            }
        }
    }


}
