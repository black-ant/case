package com.gang.kryo.kryo.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.kryo.kryo.to.CommonTO;
import com.sun.corba.se.spi.ior.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Classname KryoService
 * @Description TODO
 * @Date 2021/3/24
 * @Created by zengzg
 */
@Component
public class KryoService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> [测试 Kryo 序列化对象] <-------");

        testCommon();

//        testPool();
    }

    public void testCommon() {

//        testObject();

//        testMap();

//        testSet();


        testService();
    }

    /**
     * 准备测试一个注入的对象是否可以序列化 , 测试不可行
     */
    public void testService() {
        CommonSerializerService commonSerializerService = new CommonSerializerService();
    }

    /**
     * 反序列化 Object
     */
    public void testObject() {
        CommonSerializerService commonSerializerService = new CommonSerializerService();

        CommonTO commonTO = buildCommonTO();
        String serializeTo = commonSerializerService.serializationObject(commonTO);

        logger.info("------> [序列化完成 : 拉取返回数据 Step 1 :{}] <-------", JSONObject.toJSONString(commonSerializerService.deserializationObject(serializeTo, CommonTO.class)));
//        logger.info("------> [序列化完成 : 测试反复拉取返回数据 Step 2 :{}] <-------", JSONObject.toJSONString(commonSerializerService.deserializationObject(serializeTo, CommonTO.class)));

        CommonSerializerService commonSerializerService2 = new CommonSerializerService();
        logger.info("------> [序列化完成 : 拉取返回数据 Step 1 :{}] <-------", JSONObject.toJSONString(commonSerializerService2.deserializationObject(serializeTo, CommonTO.class)));

    }

    /**
     * 反序列化 Map
     */
    public void testMap() {
        CommonSerializerService commonSerializerService = new CommonSerializerService();
        Map<String, CommonTO> map = new HashMap<String, CommonTO>();
        for (int i = 0; i < 10; i++) {
            CommonTO val = buildCommonTO();
            map.put(String.valueOf(i), val);
        }

        String serializationMap = commonSerializerService.serializationMap(map, CommonTO.class);
        logger.info("------> [序列化完成 : 拉取返回数据 Step 1 :{}] <-------", JSONObject.toJSONString(commonSerializerService.deserializationMap(serializationMap, CommonTO.class)));
    }

    /**
     * 测试 Set 集合
     */
    public void testSet() {
        CommonSerializerService commonSerializerService = new CommonSerializerService();
        Set<CommonTO> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            CommonTO commonTO = buildCommonTO();
            set.add(commonTO);
        }
        String serialization = commonSerializerService.serializationSet(set, CommonTO.class);
        logger.info("------> [序列化完成 : 拉取返回数据 Step 1 :{}] <-------", JSONObject.toJSONString(commonSerializerService.deserializationSet(serialization, CommonTO.class)));
    }


    /**
     * 测试 Pool 方式
     * 记录 : 当内部存在空集合时 , 会直接出现空指针问题 java.lang.NullPointerException: null at java.util.Arrays$ArrayList.size(Arrays.java:3818) ~[na:1.8.0_152]
     */
    public void testPool() {

        logger.info("------> [测试 Pool 方式] <-------");
        KryoPoolSerializer poolSerializer = new KryoPoolSerializer();

        CommonTO commonTO = buildCommonTO();
        byte[] serializeTo = poolSerializer.serialize(commonTO);

        logger.info("------> [序列化完成 : 拉取返回数据 Step 1 :{}] <-------", JSONObject.toJSONString(poolSerializer.deserialize(serializeTo)));
        logger.info("------> [序列化完成 : 测试反复拉取返回数据 Step 2 :{}] <-------", JSONObject.toJSONString(poolSerializer.deserialize(serializeTo)));

        KryoPoolSerializer poolSerializer2 = new KryoPoolSerializer();
        logger.info("------> [序列化完成 : 拉取返回数据 Step 1 :{}] <-------", JSONObject.toJSONString(poolSerializer2.deserialize(serializeTo)));
    }


    public CommonTO buildCommonTO() {
        CommonTO commonTO = new CommonTO();
        commonTO.setUsername("gang" + new Random().nextInt(999));

        CommonTO commonTOInner = new CommonTO();
        commonTOInner.setUsername("child");
        commonTOInner.setUserList(new ArrayList<>());

        commonTO.setUserList(Arrays.asList(commonTOInner));
        return commonTO;
    }
}
