package com.gang.study.engine.demo.logic;

import com.gang.etl.engine.container.SyncContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Classname BeanContainerExample
 * @Description TODO
 * @Date 2020/6/20 15:39
 * @Created by zengzg
 */
@Component
public class BeanContainerExample {

    @Autowired
    private SyncContainer syncContainer;

    public Map get(String type) {
        return type.equals("produce") ? syncContainer.getProducerMap() : syncContainer.getConsumerMap();
    }

}
