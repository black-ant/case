package org.mengyun.tcctransaction.sample.dubbo.dao;


import org.mengyun.tcctransaction.sample.dubbo.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by changming.xie on 4/1/16.
 */
public interface ShopDao extends JpaRepository<Shop, Long> {

    Shop findById(long id);
}
