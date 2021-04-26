package com.gang.shardingjdbc.demo.repository;

import com.gang.shardingjdbc.demo.entity.OrderEntity;
import com.gang.shardingjdbc.demo.entity.api.Address;
import com.gang.shardingjdbc.demo.entity.api.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Classname SelfAddressRepository
 * @Description TODO
 * @Date 2021/4/26
 * @Created by zengzg
 */
public interface SelfOrderRepository extends JpaRepository<OrderEntity, Long> {

    OrderEntity findFirstByOrderByOrderIdDesc();
}
