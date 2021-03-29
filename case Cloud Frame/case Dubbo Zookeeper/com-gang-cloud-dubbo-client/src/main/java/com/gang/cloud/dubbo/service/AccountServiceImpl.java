package com.gang.cloud.dubbo.service;

import com.gang.cloud.service.IAccountService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname AccountServiceImpl
 * @Description TODO
 * @Date 2021/3/20
 * @Created by zengzg
 */
@Service
@Component
public class AccountServiceImpl implements IAccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     *
     * @return
     */
    public String serverAskClient() {
        logger.info("------> [Client 反向调用 Server 端] <-------");

        return "success";
    }
}
