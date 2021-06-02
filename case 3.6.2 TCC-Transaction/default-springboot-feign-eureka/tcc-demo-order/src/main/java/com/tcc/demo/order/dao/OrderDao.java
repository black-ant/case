package com.tcc.demo.order.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tcc.demo.order.model.Order;

/**
 * Created by changming.xie on 4/1/16.
 */
@Mapper
public interface OrderDao {

    public int insert(Order order);

    public int update(Order order);

    Order findByMerchantOrderNo(String merchantOrderNo);
}
