package com.tcc.demo.order.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tcc.demo.order.dao.ShopDao;
import com.tcc.demo.order.model.Shop;

/**
 * Created by changming.xie on 4/1/16.
 */
@Repository
public class ShopRepository {

    @Autowired
    ShopDao shopDao;

    public Shop findById(long id) {

        return shopDao.findById(id);
    }
}
