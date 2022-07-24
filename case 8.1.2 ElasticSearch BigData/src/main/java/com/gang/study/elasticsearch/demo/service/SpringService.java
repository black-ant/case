package com.gang.study.elasticsearch.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.elasticsearch.demo.entity.AOrder;
import com.gang.study.elasticsearch.demo.repository.AOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Classname SpringService
 * @Description TODO
 * @Date 2022/3/19
 * @Created by zengzg
 */
@Component
public class SpringService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AOrderRepository orderRepository;

    public List<AOrder> run() {
        List<AOrder> orderList = orderRepository.findFirstByUsername("AntBlack42");
        logger.info("------> {}  <-------", JSONObject.toJSONString(orderList));
        return orderList;
    }
}
