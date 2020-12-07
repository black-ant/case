package com.gang.protocal.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2020/11/30 17:55
 * @Created by zengzg
 */
@Service
public class StartService implements ApplicationRunner {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String TEL = "15927401369";
    private static String PRIVATE_KEY = "15927401369";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Step 1: 生成密钥
        String privateKey = GoogleAuthenticator.genSecret(TEL);
        this.PRIVATE_KEY = privateKey;
        logger.info("------> this is private key :{} <-------", privateKey);


    }

    public Boolean checkCode(String code) {
        // Step 2 : 验证
        return GoogleAuthenticator.authcode(code, this.PRIVATE_KEY);
    }
}
