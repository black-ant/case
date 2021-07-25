package com.gang.demo.nio.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname ReadByFileService
 * @Description TODO
 * @Date 2021/7/25
 * @Created by zengzg
 */
@Component
public class ReadByFileService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try {
            templateRead();

//            templateWrite();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void templateRead() throws Exception {

        logger.info("------> Step 1 : 开启基本案例 , 从 Stream 转换为  <-------");
        FileInputStream fin = new FileInputStream("src/main/resources/data/data.txt");
        FileChannel fc = fin.getChannel();

        FileInputStream fin2 = new FileInputStream("src/main/resources/data/data1.txt");
        FileChannel fc2 = fin.getChannel();

        logger.info("------> Step 2 : 构建一个缓冲区 <-------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        logger.info("------> Step 3 : 从缓冲区读取数据 <-------");
        int bytesRead = fc.read(buffer);
        System.out.println("buffer = " + buffer);

        int bytesRead2 = fc2.read(buffer);
        bytesRead = bytesRead + bytesRead2;

        logger.info("------> Step 4: 单个字符读取 <-------");
        while (bytesRead != -1) {
            buffer.flip();  //缓冲区准备读取
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());// 每次读取一个字节
            }
            buffer.clear(); //准备缓冲区写入

            // 可以通过屏蔽该语句看效果
            bytesRead = fc.read(buffer);
        }

        fin.close();

    }


    public void templateWrite() throws Exception {

        logger.info("------> Step 1 : 开启基本案例 , 从 Stream 转换为  <-------");
        RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/resources/data/data2.txt", "rw");

        // 该方式特殊字符 / 空白字符 存在问题
//        URL in = this.getClass().getResource("/data/data2.txt");
//        FileInputStream fin = new FileInputStream(in.getFile());

        FileChannel fc = randomAccessFile.getChannel();


        logger.info("------> Step 2 : 构建一个缓冲区 <-------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] message = new String("Hello World !").getBytes();
        for (int i = 0; i < message.length; ++i) {
            buffer.put(message[i]);
        }
        buffer.flip();

        logger.info("------> Step 3 : 往缓冲区写数据 <-------");
        fc.write(buffer);

    }
}
