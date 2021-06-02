package com.tcc.demo.order.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tcc.demo.order.dao.ProductDao;
import com.tcc.demo.order.model.Product;

/**
 * Created by twinkle.zhou on 16/11/10.
 */
@Repository
public class ProductRepository {

    @Autowired
    ProductDao productDao;

    public Product findById(long productId){
        return productDao.findById(productId);
    }

    public List<Product> findByShopId(long shopId){
        return productDao.findByShopId(shopId);
    }
}
