package com.gang.xxljob.demo.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname XxlJobConfig
 * @Description TODO
 * @Date 2021/10/18
 * @Created by zengzg
 */
@Configuration
public class XxlJobConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value(value = "${xxl.job.admin.addresses}")
    private String adminAddresses;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        logger.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppname("xxl-job-executor-sample");
        xxlJobSpringExecutor.setIp("192.168.158.30");
        xxlJobSpringExecutor.setPort(8080);
//        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath("/data/applogs/xxl-job/jobhandler");
        xxlJobSpringExecutor.setLogRetentionDays(30);
        return xxlJobSpringExecutor;
    }
}
