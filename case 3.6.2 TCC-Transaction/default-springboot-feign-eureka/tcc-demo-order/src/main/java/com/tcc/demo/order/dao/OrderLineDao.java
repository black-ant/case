package com.tcc.demo.order.dao;


import org.apache.ibatis.annotations.Mapper;

import com.tcc.demo.order.model.OrderLine;

/**
 * Created by changming.xie on 4/1/16.
 */
@Mapper
public interface OrderLineDao {
    void insert(OrderLine orderLine);
}
