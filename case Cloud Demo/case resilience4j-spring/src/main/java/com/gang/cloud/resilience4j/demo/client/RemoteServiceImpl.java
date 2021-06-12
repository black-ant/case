package com.gang.cloud.resilience4j.demo.client;

import com.gang.cloud.resilience4j.demo.exceptions.BusinessAException;
import com.gang.cloud.resilience4j.demo.exceptions.BusinessBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Classname RemoteServiceImpl
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
@Component
public class RemoteServiceImpl implements IRemoteService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public String process() throws TimeoutException, InterruptedException {
        int num = count.getAndIncrement();
        logger.info("count的值 = " + num);
        if (num % 4 == 1) {
            throw new BusinessAException("异常A，需要重试");
        }
        if (num % 4 == 2) {
            return null;
        }
        if (num % 4 == 3) {
            throw new BusinessBException("异常B，需要重试");
        }
        logger.info("服务正常运行，获取用户列表");
        // 模拟数据库的正常查询
        return "success";
    }
}
