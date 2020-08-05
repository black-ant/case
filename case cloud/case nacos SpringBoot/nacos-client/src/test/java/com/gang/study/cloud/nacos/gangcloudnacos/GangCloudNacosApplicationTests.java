package com.gang.study.cloud.nacos.gangcloudnacos;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GangCloudNacosApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    void contextLoads() {
        logger.info("------> this is in  GangCloudNacosApplicationTests<-------");
    }

}
