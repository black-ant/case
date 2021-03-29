package org.mengyun.tcctransaction.sample.dubbo.repository;


import org.mengyun.tcctransaction.sample.dubbo.dao.OrderDao;
import org.mengyun.tcctransaction.sample.dubbo.dao.OrderLineDao;
import org.mengyun.tcctransaction.sample.dubbo.entity.Order;
import org.mengyun.tcctransaction.sample.dubbo.entity.OrderLine;
import org.mengyun.tcctransaction.sample.exception.InsufficientBalanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by changming.xie on 4/1/16.
 */
@Repository
public class OrderRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderLineDao orderLineDao;

    public void createOrder(Order order) {
        orderDao.save(order);

        for (OrderLine orderLine : order.getOrderLines()) {
            orderLineDao.save(orderLine);
        }
    }

    public void updateOrder(Order order) {
        try {
            orderDao.save(order);
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            throw new InsufficientBalanceException();
        }
    }

    public Order findByMerchantOrderNo(String merchantOrderNo) {
        return orderDao.findByMerchantOrderNo(merchantOrderNo);
    }
}
