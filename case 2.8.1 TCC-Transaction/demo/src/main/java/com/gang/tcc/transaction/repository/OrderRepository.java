package com.gang.tcc.transaction.repository;


import com.gang.tcc.transaction.entity.Order;
import org.mengyun.tcctransaction.sample.order.domain.entity.Order;
import org.mengyun.tcctransaction.sample.order.domain.entity.OrderLine;
import org.mengyun.tcctransaction.sample.order.infrastructure.dao.OrderDao;
import org.mengyun.tcctransaction.sample.order.infrastructure.dao.OrderLineDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by changming.xie on 4/1/16.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    public Order findByMerchantOrderNo(String merchantOrderNo);
}
