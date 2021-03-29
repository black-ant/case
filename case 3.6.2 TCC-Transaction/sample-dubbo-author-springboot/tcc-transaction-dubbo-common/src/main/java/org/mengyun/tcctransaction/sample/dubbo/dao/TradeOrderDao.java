package org.mengyun.tcctransaction.sample.dubbo.dao;


import org.mengyun.tcctransaction.sample.dubbo.entity.TradeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by twinkle.zhou on 16/11/14.
 */
public interface TradeOrderDao extends JpaRepository<TradeOrder, Long> {

    TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
