package com.tcc.demo.order.dao;


import org.apache.ibatis.annotations.Mapper;

import com.tcc.demo.order.model.Shop;

@Mapper
public interface ShopDao {
    Shop findById(long id);
}
