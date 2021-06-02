package com.tcc.demo.redpacket.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import com.tcc.demo.redpacket.dao.TradeOrderDao;
import com.tcc.demo.redpacket.model.TradeOrder;

/**
 * Created by twinkle.zhou on 16/11/14.
 */
@Repository
public class TradeOrderRepository {

    @Autowired
    TradeOrderDao tradeOrderDao;

    public void insert(TradeOrder tradeOrder) {
        tradeOrderDao.insert(tradeOrder);
    }

    public void update(TradeOrder tradeOrder) {

        tradeOrder.updateVersion();
        int effectCount = tradeOrderDao.update(tradeOrder);
        if (effectCount < 1) {
            throw new OptimisticLockingFailureException("update trade order failed");
        }
    }

    public TradeOrder findByMerchantOrderNo(String merchantOrderNo) {
        return tradeOrderDao.findByMerchantOrderNo(merchantOrderNo);
    }

}
