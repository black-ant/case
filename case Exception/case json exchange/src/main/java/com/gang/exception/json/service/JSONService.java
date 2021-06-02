package com.gang.exception.json.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gang.exception.json.entity.TestTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname JSONService
 * @Description TODO
 * @Date 2020/12/11 16:55
 * @Created by zengzg
 */
@Component
public class JSONService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper mapper = new ObjectMapper();

    public TestTO getExtendFieldObjFromJson(String extendFieldJson) {
        TestTO testTO = null;
        try {
            if (extendFieldJson.startsWith("{")) {
                testTO = mapper.readValue(extendFieldJson, TestTO.class);
            }
        } catch (Exception e) {
            // 判断抛出的异常是否会透传到tomcat 中
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            logger.error("E----> {}", e);
            e.printStackTrace();
        }
        return testTO;
    }
}
