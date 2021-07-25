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
 * @Classname FileChannelService
 * @Description TODO
 * @Date 2021/7/25
 * @Created by zengzg
 */
@Component
public class FileChannelService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        templateRead();
    }

    public void templateRead() throws Exception {

        // 打开 FileChannel
        // 通过使用一个InputStream、OutputStream或RandomAccessFile来获取一个FileChannel实例
        RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        // 从FileChannel读取数据
        // 1 首先，分配一个Buffer。从FileChannel中读取的数据将被读到Buffer中。
        // 2 然后，调用FileChannel.read()方法。该方法将数据从FileChannel读取到Buffer中
        // 返回-1，表示到了文件末尾
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = inChannel.read(buf);



    }

}
