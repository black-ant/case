package com.gang.study.rmi.demo.invoke.server;

import com.gang.study.rmi.demo.invoke.api.CapitalAccountServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @Classname RemoteRun
 * @Description Lazy 的目的是保证 服务端比客户端先创建 , 否则会连接失败
 * @Date 2021/3/17
 * @Created by zengzg
 */
@Component
@Lazy
public class RemoteRun implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CapitalAccountServiceClient capitalAccountServiceClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> [Remote Run 运行 ...] <-------");

        doRemote();
    }

    /**
     * 调用远程接口 , 这里使用的是另外一个接口 , 以区别远程和本地
     */
    public void doRemote() {
        logger.info("------> [Remote 调用完成 :{}] <-------", capitalAccountServiceClient.getCapitalAccountByUserId(1L));
    }
}
