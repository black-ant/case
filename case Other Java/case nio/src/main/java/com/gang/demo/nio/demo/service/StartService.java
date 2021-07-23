package com.gang.demo.nio.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/7/23
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try {
            testFileChannel();
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }

    }

    /**
     * 测试简单得 FileChannel
     *
     * @throws Exception
     */
    public void testFileChannel() throws Exception {
        // 建立管道
        RandomAccessFile aFile = new RandomAccessFile("classpath:data/data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf);
        // 读取内容 ，并且关闭
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
    }
}
