package com.gang.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static java.lang.Math.toIntExact;

/**
 * @Classname LongLogic
 * @Description TODO
 * @Date 2020/11/2 10:38
 * @Created by zengzg
 */
@Component
public class LongLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //long 到 int 的溢出基本就只能溢出了,  数据已经超过int 的最大值了 ,无解 , 只能考虑抛出异常
        longToInt();
    }

    /**
     * long, int               　　b=(int)a;
     * long,Integer 　　       b= new Long(a).intValue();
     * Long,int                      b= a.inValue();
     * Long,Integer              b= a.intValue()
     * int, long　　　　　  b= a
     * int,Long                     b= new Integer(a).LongValue();
     * Integer,long　　       b= a.longValue()
     * Inter,long                 b= a.longValue();
     */
    public void longToInt() {
        long testLong = 100000L;

        int testInt = (int) testLong;
        logger.info("------> this test :{} <-------", testInt);

        // Integer.MAX_VALUE = 2147483647
        int a = Integer.MAX_VALUE;
        int b1 = 1;
        long sum1 = a + b1;

        long sum2 = 2147483647 + 1;

        long b2 = 1;
        long sum3 = a + b2;

        long sum4 = 2147483647 + 1L;

        System.out.println("int 类型 add : sum1 = " + sum1);
        System.out.println("int 类型 add : sum2 = " + sum2);
        System.out.println("Long 类型 add : sum3 = " + sum3);
        System.out.println("Long 类型 add : sum4 = " + sum4);
        System.out.println("Long 类型 转 int : sum5 = " + (int) sum4);
        System.out.println("Long 类型 转 int : sum6 = " + new Long(sum4).intValue());

        // 溢出异常 ,安全处理
        try {
            int bar = toIntExact(sum4);
        } catch (ArithmeticException e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
        }
    }
}
