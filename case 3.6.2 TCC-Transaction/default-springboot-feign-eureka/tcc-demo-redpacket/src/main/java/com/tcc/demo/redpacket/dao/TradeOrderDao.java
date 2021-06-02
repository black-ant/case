package com.tcc.demo.redpacket.dao;


import org.apache.ibatis.annotations.Mapper;

import com.tcc.demo.redpacket.model.TradeOrder;

/**
 * Created by twinkle.zhou on 16/11/14.
 */
@Mapper
public interface TradeOrderDao {

    void insert(TradeOrder tradeOrder);

    int update(TradeOrder tradeOrder);

    TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
