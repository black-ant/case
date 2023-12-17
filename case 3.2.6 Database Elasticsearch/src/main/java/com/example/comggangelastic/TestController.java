package com.example.comggangelastic;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2023/2/2
 * @Created by zengzg
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private MessageSendRepository messageSendRepository;

    @GetMapping
    public void test() {
        log.info("------> ok  <-------");
        MessageSendEntity sendEntity = new MessageSendEntity();
        sendEntity.setRequestId("123");
        sendEntity.setMsgChannel("123");
        sendEntity.setAppId("123");
        sendEntity.setMsgTitle("123");
        sendEntity.setMsgContent("123");
        sendEntity.setMsgTarget("123");
        sendEntity.setBatchNo("123");
        sendEntity.setCreateTime(new Date());
        sendEntity.setUpdateTime(new Date());
        sendEntity.setPushStatusCode("123");
        sendEntity.setPushResponseBody("123");
        messageSendRepository.save(sendEntity);
    }


    @GetMapping("batch")
    public void batch() {
        log.info("------> batch  <-------");

        ExecutorService threadPool = Executors.newFixedThreadPool(5);


        threadPool.submit(() -> {
            for (int i = 0; i < 300000; i++) {
                save("apple");
            }
        });

        threadPool.submit(() -> {
            for (int i = 0; i < 300000; i++) {
                save("sms");
            }
        });


        threadPool.submit(() -> {
            for (int i = 0; i < 300000; i++) {
                save("boy");
            }
        });


        threadPool.submit(() -> {
            for (int i = 0; i < 300000; i++) {
                save("good");
            }
        });


        threadPool.submit(() -> {
            for (int i = 0; i < 300000; i++) {
                save("one");
            }
        });

    }

    private void save(String type) {
        MessageSendEntity sendEntity = new MessageSendEntity();
        sendEntity.setRequestId("123");
        sendEntity.setMsgChannel(type);
        sendEntity.setAppId("123");
        sendEntity.setMsgTitle(type + new Random().nextInt(9));
        sendEntity.setMsgContent("123");
        sendEntity.setMsgTarget("123");
        sendEntity.setBatchNo("123");
        sendEntity.setCreateTime(new Date());
        sendEntity.setUpdateTime(new Date());
        sendEntity.setPushStatusCode("" + new Random().nextInt(9));
        sendEntity.setPushResponseBody("123");
        messageSendRepository.save(sendEntity);
        log.info("------> save ok  <-------");
    }


}
