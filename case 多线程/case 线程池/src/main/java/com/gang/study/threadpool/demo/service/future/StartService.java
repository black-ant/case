package com.gang.study.threadpool.demo.service.future;

import com.gang.study.threadpool.demo.service.CachedThreadPoolService;
import com.gang.study.threadpool.demo.service.FixedThreadPoolService;
import com.gang.study.threadpool.demo.service.SingleThreadPoolService;
import com.gang.study.threadpool.demo.service.SingleThreadScheduledExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2019/11/1 9:54
 * @Created by ant-black 1016930479@qq.com
 */
@Service
public class StartService implements ApplicationRunner {

    @Autowired
    private FixedThreadPoolService threadPoolService;

    @Autowired
    private SingleThreadPoolService singleThreadPoolService;

    @Autowired
    private CachedThreadPoolService cachedThreadPoolService;

    @Autowired
    private SingleThreadScheduledExecutorService singleThreadScheduledExecutorService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //        singleThreadPoolService.run();
        //        threadPoolService.run();
        //        cachedThreadPoolService.run();
        //        singleThreadScheduledExecutorService.run();
    }
}
