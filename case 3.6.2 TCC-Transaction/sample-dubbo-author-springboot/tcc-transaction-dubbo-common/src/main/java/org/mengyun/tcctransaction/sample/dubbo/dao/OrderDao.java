package org.mengyun.tcctransaction.sample.dubbo.dao;


import org.mengyun.tcctransaction.sample.dubbo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by changming.xie on 4/1/16.
 */
public interface OrderDao extends JpaRepository<Order, Long> {

    Order findByMerchantOrderNo(String merchantOrderNo);
}
