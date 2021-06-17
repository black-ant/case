package com.gang.acttivity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActtivityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActtivityApplication.class, args);
    }

    /**
     * 主要运行类 : StartController
     * 账户秘密 : root/123456
     * 请求地址 :
     * 获取流程信息 : 127.0.0.1:8086/info
     * 开启流程信息 : 127.0.0.1:8086/startFlow
     * 执行流程信息 : 127.0.0.1:8086/doFlow
     */

}
