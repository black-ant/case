package com.gang.xxljob.demo.service;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@JobHandler(value = "testJobHandler")
@Component
@Service
public class TestHandleService extends IJobHandler {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        logger.info("param is :{}", s);
        return null;
    }
}
