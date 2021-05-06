package com.gang.study.sharding.demo.service;

import com.alibaba.fastjson.JSON;
import com.gang.study.sharding.demo.dao.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Classname TestServiceImpl
 * @Description TODO
 * @Date 2021/5/5
 * @Created by zengzg
 */
@Service("TestService")
public class TestServiceImpl {

    @Autowired
    TestMapper testMapper;

    public void test3() {
        for (int i = 0; i < 10; i++) {
            Map<String, Object> paramsMap = new HashMap<String, Object>();
            //paramsMap.put("id", "2");
            paramsMap.put("columnId", new Random().nextInt(999));
            paramsMap.put("title", "标题2");
            paramsMap.put("author", "zhaohy2");
            paramsMap.put("titleId", i);
            testMapper.insertTest(paramsMap);
            System.out.println("插入完成！");
        }
    }


    public void select() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        list = testMapper.getListByDb(paramsMap);
        System.out.println("===" + JSON.toJSONString(list));
    }

}
