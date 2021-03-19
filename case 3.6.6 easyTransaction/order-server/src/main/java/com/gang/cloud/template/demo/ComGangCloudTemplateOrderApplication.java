package com.gang.cloud.template.demo;

import com.yiqiniu.easytrans.EnableEasyTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableEasyTransaction
@EnableTransactionManagement
public class ComGangCloudTemplateOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComGangCloudTemplateOrderApplication.class, args);
    }


    /**
     * 问题笔记 :
     * 1 . class path resource [org/springframework/boot/autoconfigure/web/ServerPropertiesAutoConfiguration.class] cannot be opened because it does not exist
     * --- Spring AutoConfiguration 1.5.2 , 查看 pom 发现 Spring 统一版本为 4.2 ,适应高版本需要定制
     * 2 . java.lang.ClassNotFoundException: org.springframework.boot.bind.RelaxedPropertyResolver
     * --- 同样版本问题 回退 SpringBoot 版本测试 , 同时需要移除 Cloud 组件
     * 3 . 整体版本过老
     */
//    ServerPropertiesAutoConfiguration serverPropertiesAutoConfiguration;
}
