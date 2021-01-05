package com.gang.study.module.gangstudymodule.service;

import com.alibaba.fastjson.JSON;
import com.gang.study.module.gangstudymodule.dao.MyOrderMapper;
import com.gang.study.module.gangstudymodule.entity.MyOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/20 18:07
 * @Version 1.0
 **/
@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MyOrderMapper orderMapper;

    public List<MyOrder> findAll() {
        return orderMapper.findAll();
    }

}
