package com.gang.seata.sharding.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {


    /**
     * 参考文档 : https://www.cnblogs.com/architectforest/p/13639203.html
     *
     * Step 1 : 访问列表 http://127.0.0.1:8080/home/orderlist
     * Step 2 : 正常访问 http://127.0.0.1:8080/home/addorder?orderid=400
     * Step 3 : 异常回滚 http://127.0.0.1:8080/home/addorder?orderid=401&isfail=1
     *
     *
     * url 分布式事务 :
     * - http://127.0.0.1:8080/home/addorderrest?orderid=402
     * - http://127.0.0.1:8080/home/addorderrest?orderid=403&isfail=1
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
