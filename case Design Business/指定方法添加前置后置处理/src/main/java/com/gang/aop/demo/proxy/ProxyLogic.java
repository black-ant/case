package com.gang.aop.demo.proxy;

import com.gang.aop.demo.entity.LogicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname ProxyLogic
 * @Description TODO
 * @Date 2020/10/28 17:25
 * @Created by zengzg
 */
@Component
public class ProxyLogic implements ApplicationRunner {

    @Autowired
    private ExInterface execute;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        LogicEntity entity = new LogicEntity("gang", "username");
        execute.execute(entity);
    }
}
