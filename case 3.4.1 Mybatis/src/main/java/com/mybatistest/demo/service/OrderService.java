package com.mybatistest.demo.service;

import com.alibaba.fastjson.JSON;
import com.mybatistest.demo.entity.MyOrder;
import com.mybatistest.demo.mapper.MyOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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

    public List<MyOrder> findByOrderName(String name) {
        return orderMapper.orderList(name);
    }

    public List<MyOrder> findAll() {
        return orderMapper.findAll();
    }

    public List<MyOrder> findTwoTable() {
        return orderMapper.findTwoTable();
    }

    public List<MyOrder> findTwoTableMany() {
        return orderMapper.findTwoTableMany();
    }

    public List<MyOrder> findInclude(Date start) {
        return orderMapper.findInclude(start);
    }

    public List<MyOrder> findNoParam(String key, String value) {
        logger.info("查询key:{} -value is :{}", key, value);
        return orderMapper.findNoParam(key, value);
    }

    public int insertOne(MyOrder order) {
        return orderMapper.insertone(order);
    }

    public int insertSearch(MyOrder order) {
        return orderMapper.insertSearch(order);
    }

    public int insertOneReturnId(MyOrder order) {
        Integer num = orderMapper.insertOneReturnId(order);
        logger.info("order id is :{} - ID 不为 null 说明返回成功", order.getId());
        return num;
    }

    public int insertMany(List<MyOrder> order) {
        Integer num = orderMapper.insertMany(order);
        order.stream().forEach(p -> {
            logger.info("order id is:{}--ordername is :{}", p.getId(), p.getOrdername());
        });
        return num;
    }


    public int updateDescByid(int id, String desc) {
        logger.info("更新数据 : id :{}--desc:{}", id, desc);
        Integer num = orderMapper.updateParamDescByid(id, desc);
        return num;
    }


    public int updateNumDescByid(int id, String desc) {
        logger.info("更新数据 : id :{}--desc:{}", id, desc);
        Integer num = orderMapper.updateNumDescByid(id, desc);
        return num;
    }

    public int updateMany(MyOrder order) {
        logger.info("更新数据 order :{}", JSON.toJSONString(order));
        Integer num = orderMapper.updateMany(order);
        return num;
    }

    public int deleteByPrimaryKey(int id) {
        logger.info("删除数据 : id :{}", id);
        Integer num = orderMapper.deleteByPrimaryKey(id);
        return num;
    }
}
