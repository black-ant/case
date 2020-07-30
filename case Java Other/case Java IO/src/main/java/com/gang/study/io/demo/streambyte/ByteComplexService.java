package com.gang.study.io.demo.streambyte;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname ComplexService
 * @Description TODO
 * @Date 2020/7/30 10:02
 * @Created by zengzg
 */
@Component
public class ByteComplexService implements ApplicationRunner {

    @Autowired
    private FileInputStreamService fileInputStreamService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        fileInputStreamService.input("");
    }
}
