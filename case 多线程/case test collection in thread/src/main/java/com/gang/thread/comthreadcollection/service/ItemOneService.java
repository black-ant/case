package com.gang.thread.comthreadcollection.service;

import com.gang.thread.comthreadcollection.api.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Classname ItemOneService
 * @Description TODO
 * @Date 2020/11/25 10:44
 * @Created by zengzg
 */
@Component
public class ItemOneService implements ItemService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String test(String name) {
        for (int i = 0; i < 10; i++) {

            try {
                Thread.sleep(10 * new Random().nextInt(30));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("------> ItemOneService name :{}<-------", name);
        return "scuccess";
    }
}
