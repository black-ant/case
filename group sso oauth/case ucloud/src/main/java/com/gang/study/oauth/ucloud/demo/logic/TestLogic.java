package com.gang.study.oauth.ucloud.demo.logic;

import cn.ucloud.common.pojo.Account;
import cn.ucloud.udisk.client.DefaultUdiskClient;
import cn.ucloud.udisk.client.UdiskClient;
import cn.ucloud.udisk.model.DescribeUDiskParam;
import cn.ucloud.udisk.model.DescribeUDiskResult;
import cn.ucloud.udisk.pojo.UdiskConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/4/29 14:17
 * @Created by zengzg
 */
@Component
public class TestLogic implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        UdiskClient client = new DefaultUdiskClient(new UdiskConfig(
                new Account("PrivateKey", "PublicKey")
        ));
        DescribeUDiskParam param = new DescribeUDiskParam("region");
        param.setProjectId("projectId");
        DescribeUDiskResult describeUDisk = null;
        try {
            describeUDisk = client.describeUDisk(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(describeUDisk);
    }
}
