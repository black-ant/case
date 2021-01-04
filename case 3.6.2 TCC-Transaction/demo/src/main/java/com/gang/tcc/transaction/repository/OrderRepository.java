package com.gang.tcc.transaction.repository;


import com.gang.tcc.transaction.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by changming.xie on 4/1/16.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByMerchantOrderNo(String merchantOrderNo);
}
