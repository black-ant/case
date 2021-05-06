package com.gang.study.sharding.demo.dao;

import java.util.List;
import java.util.Map;

/**
 * @Classname TestMapper
 * @Description TODO
 * @Date 2021/5/5
 * @Created by zengzg
 */
public interface TestMapper {

    List<Map<String, Object>> getListByDb(Map<String, Object> paramsMap);

    void insertTest(Map<String, Object> paramsMap);

}
