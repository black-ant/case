package org.mengyun.tcctransaction.sample.http.redpacket.dao;


import org.mengyun.tcctransaction.sample.http.redpacket.entity.TradeOrder;

/**
 * Created by twinkle.zhou on 16/11/14.
 */
public interface TradeOrderDao {

    void insert(TradeOrder tradeOrder);

    int update(TradeOrder tradeOrder);

    TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
