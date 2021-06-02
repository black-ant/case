package com.tcc.demo.capital.dao;


import org.apache.ibatis.annotations.Mapper;

import com.tcc.demo.capital.model.TradeOrder;

/**
 * Created by twinkle.zhou on 16/11/14.
 */
@Mapper
public interface TradeOrderDao {

    int insert(TradeOrder tradeOrder);

    int update(TradeOrder tradeOrder);

    TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
