package com.gang.jvm.jvmjol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2020/12/10 9:27
 * @Created by zengzg
 */
@Component
public class TestService implements ApplicationRunner, Serializable {

    private static final long serialVersionUID = -4223319704861765405L;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Object generate() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", new Integer(1));
        map.put("b", "b");
        map.put("c", new Date());

        for (int i = 0; i < 10; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }
        return map;
    }

    public void test() {
        //查看对象内部信息：
        String table = ClassLayout.parseInstance(this).toPrintable();
        logger.info("------> table :{} <-------", table);
        // 查看对象外部信息：包括引用的对象：
        String print = GraphLayout.parseInstance(this).toPrintable();
        logger.info("------> info :{} <-------", print);
        // 查看对象占用空间总大小：
        long total = GraphLayout.parseInstance(this).totalSize();
        logger.info("------> total :{} <-------", total);


        logger.info("------> this :{} <-------", this.getSerialVersionUID());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        generate();
        test();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
