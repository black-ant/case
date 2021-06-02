package com.tcc.demo.order.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tcc.demo.order.model.Product;

/**
 * Created by twinkle.zhou on 16/11/10.
 */
@Mapper
public interface ProductDao {

    Product findById(long productId);

    List<Product> findByShopId(long shopId);
}
