package com.tcc.demo.order.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.mengyun.tcctransaction.CancellingException;
import org.mengyun.tcctransaction.ConfirmingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.demo.order.model.Order;
import com.tcc.demo.order.model.Shop;
import com.tcc.demo.order.repository.ShopRepository;

/**
 * Created by changming.xie on 4/1/16.
 */
@Service
public class PlaceOrderServiceImpl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    PaymentServiceImpl paymentService;


    public String placeOrder(long payerUserId, long shopId, List<Pair<Long, Integer>> productQuantities, BigDecimal redPacketPayAmount) {
        Shop shop = shopRepository.findById(shopId);


        logger.info("------> 构建 Order 第一阶段 , 该阶段在事务管理之外 <-------");
        Order order = orderService.createOrder(payerUserId, shop.getOwnerUserId(), productQuantities);

        Boolean result = false;

        try {
            paymentService.makePayment(order, redPacketPayAmount, order.getTotalAmount().subtract(redPacketPayAmount));

        } catch (ConfirmingException confirmingException) {
            //exception throws with the tcc transaction status is CONFIRMING,
            //when tcc transaction is confirming status,
            // the tcc transaction recovery will try to confirm the whole transaction to ensure eventually consistent.
            logger.error("E----> confirmingException error :{} -- content :{}", confirmingException.getClass() , confirmingException.getMessage());
            result = true;
        } catch (CancellingException cancellingException) {
            //exception throws with the tcc transaction status is CANCELLING,
            //when tcc transaction is under CANCELLING status,
            // the tcc transaction recovery will try to cancel the whole transaction to ensure eventually consistent.
            logger.error("E----> confirmingException error :{} -- content :{}", cancellingException.getClass() , cancellingException.getMessage());
        } catch (Throwable e) {
            //other exceptions throws at TRYING stage.
            //you can retry or cancel the operation.
            logger.error("E----> error :{} -- content :{}", e.getClass() , e.getMessage());
            e.printStackTrace();
        }

        return order.getMerchantOrderNo();
    }
}
