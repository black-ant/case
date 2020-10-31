package org.mengyun.tcctransaction.sample.http.dao;


import org.mengyun.tcctransaction.sample.http.entity.OrderLine;

/**
 * Created by changming.xie on 4/1/16.
 */
public interface OrderLineDao {
    void insert(OrderLine orderLine);
}
