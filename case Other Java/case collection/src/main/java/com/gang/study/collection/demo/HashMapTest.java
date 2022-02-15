package com.gang.study.collection.demo;

import com.gang.study.collection.demo.to.HashTO;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname HashMapTest
 * @Description TODO
 * @Date 2021/9/9
 * @Created by zengzg
 */
@Component
public class HashMapTest implements ApplicationRunner {

    HashMap hashMap = new HashMap();

    private ConcurrentHashMap<HashTO, String> hashMap = new ConcurrentHashMap<HashTO, String>();

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        for (int i = 0; i < 10; i++) {
//            HashTO hashTO = new HashTO();
//            hashTO.setValue(i + "-Ant");
//            hashMap.put(hashTO, "Test");
//        }

    }


}
