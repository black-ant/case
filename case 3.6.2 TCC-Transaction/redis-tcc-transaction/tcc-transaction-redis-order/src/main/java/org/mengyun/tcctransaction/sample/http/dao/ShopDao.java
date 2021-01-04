package org.mengyun.tcctransaction.sample.http.dao;


import org.mengyun.tcctransaction.sample.http.entity.Shop;

/**
 * Created by changming.xie on 4/1/16.
 */
public interface ShopDao {
    Shop findById(long id);
}
