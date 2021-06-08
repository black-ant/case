package com.tcc.demo.order.service;

import java.math.BigDecimal;
import java.util.Calendar;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.mengyun.tcctransaction.api.Compensable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcc.demo.order.dto.CapitalTradeOrderDto;
import com.tcc.demo.order.dto.RedPacketTradeOrderDto;
import com.tcc.demo.order.model.Order;
import com.tcc.demo.order.repository.OrderRepository;

/**
 * Created by changming.xie on 4/1/16.
 */
@Service
public class PaymentServiceImpl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TradeOrderServiceProxy tradeOrderServiceProxy;

    @Autowired
    OrderRepository orderRepository;


    /**
     * 此处考虑后应该是要去掉小事务管理的的 (@Transaction) <br>
     * 原因 : 如果此处存在事务 , 分布式应用上抛出异常 , 则回导致该事务回滚
     * <p>
     * 但是!!! 你可能这个时候会想 , 既然出现异常这个类会回滚 , 那不相当于分布式实现了吗 , 为什么还要加个框架处理
     * <p>
     * 原因 : 此处如果考虑红包的逻辑就对了 , 这个场景实际上为 : 余额足够 ,但是红包不够的情况!!!
     *
     * @param order
     * @param redPacketPayAmount
     * @param capitalPayAmount
     */
    @Compensable(confirmMethod = "confirmMakePayment", cancelMethod = "cancelMakePayment", asyncConfirm = false, asyncCancel = false)
    public void makePayment(Order order, BigDecimal redPacketPayAmount, BigDecimal capitalPayAmount) {


        logger.info("order try make payment called.time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));
        logger.info("------> 开始事务管理 : 红包支付金额 [{}] / 账户支付余额 [{}] <-------", redPacketPayAmount, capitalPayAmount);
        //check if the order status is DRAFT, if no, means that another call makePayment for the same order happened, ignore this call makePayment.
        if (order.getStatus().equals("DRAFT")) {
            order.pay(redPacketPayAmount, capitalPayAmount);
            try {
                orderRepository.updateOrder(order);
            } catch (OptimisticLockingFailureException e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            }
        }

        logger.info("------> 订单处理完成 :[{}] <-------", order.getId());
        String result = tradeOrderServiceProxy.record(null, buildCapitalTradeOrderDto(order));
        logger.info("------> 余额消费完成 :[{}] <-------", result);
        String result2 = tradeOrderServiceProxy.record(null, buildRedPacketTradeOrderDto(order));
        logger.info("------> 红包消费完成 :[{}] <-------", result2);
    }

    public void confirmMakePayment(Order order, BigDecimal redPacketPayAmount, BigDecimal capitalPayAmount) {

        logger.warn("------> [进入 PayConfirm 流程] <-------");
//        try {
//            Thread.sleep(10001);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        logger.warn("order confirm make payment called. time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        Order foundOrder = orderRepository.findByMerchantOrderNo(order.getMerchantOrderNo());

        //check order status, only if the status equals DRAFT, then confirm order
        if (foundOrder != null && foundOrder.getStatus().equals("PAYING")) {
            order.confirm();
            orderRepository.updateOrder(order);
        }
    }

    public void cancelMakePayment(Order order, BigDecimal redPacketPayAmount, BigDecimal capitalPayAmount) {

        logger.error("------> [进入 cancel 流程] <-------");
        logger.error("order cancel make payment called.time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        Order foundOrder = orderRepository.findByMerchantOrderNo(order.getMerchantOrderNo());
        logger.info("------> [cancel 流程处理 Order :{}] <-------", JSONObject.toJSONString(foundOrder));

        if (foundOrder != null && foundOrder.getStatus().equals("PAYING")) {
            order.cancelPayment();
            orderRepository.updateOrder(order);
        }
    }


    private CapitalTradeOrderDto buildCapitalTradeOrderDto(Order order) {

        CapitalTradeOrderDto tradeOrderDto = new CapitalTradeOrderDto();
        tradeOrderDto.setAmount(order.getCapitalPayAmount());
        tradeOrderDto.setMerchantOrderNo(order.getMerchantOrderNo());
        tradeOrderDto.setSelfUserId(order.getPayerUserId());
        tradeOrderDto.setOppositeUserId(order.getPayeeUserId());
        tradeOrderDto.setOrderTitle(String.format("order no:%s", order.getMerchantOrderNo()));

        return tradeOrderDto;
    }

    private RedPacketTradeOrderDto buildRedPacketTradeOrderDto(Order order) {
        RedPacketTradeOrderDto tradeOrderDto = new RedPacketTradeOrderDto();
        tradeOrderDto.setAmount(order.getRedPacketPayAmount());
        tradeOrderDto.setMerchantOrderNo(order.getMerchantOrderNo());
        tradeOrderDto.setSelfUserId(order.getPayerUserId());
        tradeOrderDto.setOppositeUserId(order.getPayeeUserId());
        tradeOrderDto.setOrderTitle(String.format("order no:%s", order.getMerchantOrderNo()));

        return tradeOrderDto;
    }
}
