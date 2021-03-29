package com.gang.cloud.template.demo.service;

import com.gang.cloud.template.demo.client.AccountFeignClient;
import com.gang.cloud.template.demo.client.ProductFeignClient;
import com.gang.cloud.template.demo.entity.OrderEntity;
import com.gang.cloud.template.demo.repository.OrderRepository;
import com.gang.cloud.template.to.CommonAccountTO;
import com.gang.cloud.template.to.CommonProductTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @Classname BuySingleProduct
 * @Description TODO
 * @Created by zengzg
 */
@Service
public class BuyProductService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountFeignClient accountFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Transactional
    @Compensable(confirmMethod = "confirmMakePayment", cancelMethod = "cancelMakePayment", asyncConfirm = true)
    public String buySingleProduct(String accountId, String productId) {

        String responseMsg = "购买中...";
        CommonProductTO CommonProductTO = productFeignClient.getById(productId);

        if (CommonProductTO == null) {
            responseMsg = "产品不存在";
        } else if (CommonProductTO.getProductNum().compareTo(BigDecimal.ONE) < 0) {
            responseMsg = "产品库存不足";
        } else {
            OrderEntity dataOrder = new OrderEntity();
            dataOrder.setAccountId(accountId);
            dataOrder.setProductId(productId);
            dataOrder.setProductNum(BigDecimal.ONE);
            dataOrder.setTotalAmount(CommonProductTO.getUnitPrice().multiply(BigDecimal.ONE));
            orderRepository.save(dataOrder);
            logger.info("------> [Order 下单完成 : {}] <-------", dataOrder.getOrderId());

            accountFeignClient.buyProduct(dataOrder.getOrderId());
            productFeignClient.buyProduct(dataOrder.getOrderId());

            CommonAccountTO CommonAccountTO = accountFeignClient.getById("1");
            CommonProductTO productTONew = productFeignClient.getById("1");
            responseMsg = "交易完成 ! [账户余额 :{} " + CommonAccountTO.getAccountAssets().intValue() + "]" + " [ 货品余额 :{} " + productTONew.getProductNum().intValue() + "]";
        }
        return responseMsg;
    }

    public void confirmMakePayment(Order order, BigDecimal redPacketPayAmount, BigDecimal capitalPayAmount) {


        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("order confirm make payment called. time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        Order foundOrder = orderRepository.findByMerchantOrderNo(order.getMerchantOrderNo());

        //check if the trade order status is PAYING, if no, means another call confirmMakePayment happened, return directly, ensure idempotency.
        if (foundOrder != null && foundOrder.getStatus().equals("PAYING")) {
            order.confirm();
            orderRepository.updateOrder(order);
        }
    }

    public void cancelMakePayment(Order order, BigDecimal redPacketPayAmount, BigDecimal capitalPayAmount) {

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("order cancel make payment called.time seq:" + DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));

        Order foundOrder = orderRepository.findByMerchantOrderNo(order.getMerchantOrderNo());

        //check if the trade order status is PAYING, if no, means another call cancelMakePayment happened, return directly, ensure idempotency.
        if (foundOrder != null && foundOrder.getStatus().equals("PAYING")) {
            order.cancelPayment();
            orderRepository.updateOrder(order);
        }
    }
}
