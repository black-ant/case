package com.mybatistest.demo.service;

import com.mybatistest.demo.entity.MyOrder;
import com.mybatistest.demo.mapper.MyOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/20 18:07
 * @Version 1.0
 **/
@Service
public class OrderService {

    @Autowired
    MyOrderMapper orderMapper;

    public List<MyOrder> findByOrderName(String name){
        return  orderMapper.orderList(name);
    }

    public List<MyOrder> findAll(){
        return  orderMapper.findAll();
    }
}
