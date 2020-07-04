package com.gang.study.io.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class StartService implements ApplicationRunner {


    @Autowired
    FileService fileService;

    @Autowired
    InputOutputService inputOutputService;

    @Autowired
    ReadWriteService readWriteService;

    @Autowired
    BigFile bigFile;

    @Autowired
    private SimpleInputStreamService simpleInputStreamService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        fileService.run();
        //        inputOutputService.run();
        //        readWriteService.run();
        //        bigFile.run();
    }
}
