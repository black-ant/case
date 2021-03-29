package com.gang.cloud.template.demo.service;

import com.gang.cloud.template.api.AccountClient;
import com.gang.cloud.template.api.ProductClient;
import com.gang.cloud.template.demo.entity.OrderEntity;
import com.gang.cloud.template.demo.repository.OrderRepository;
import com.gang.cloud.template.to.CommonProductTO;
import org.apache.dubbo.config.annotation.Reference;
import org.mengyun.tcctransaction.api.Compensable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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

    @Reference(lazy = true, check = false)
    private AccountClient accountClient;

    @Reference(lazy = true, check = false)
    private ProductClient productClient;

    @Compensable(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod", asyncConfirm = true)
    public String buySingleProduct(String accountId, Integer productId) {

        String responseMsg = "购买中...";
        CommonProductTO CommonProductTO = productClient.getOne(productId);

//        if (CommonProductTO == null) {
//            responseMsg = "产品不存在";
//        } else if (CommonProductTO.getProductNum().compareTo(BigDecimal.ONE) < 0) {
//            responseMsg = "产品库存不足";
//        } else {
        OrderEntity dataOrder = new OrderEntity();
        dataOrder.setAccountId(accountId);
        dataOrder.setProductId(String.valueOf(productId));
        dataOrder.setProductNum(BigDecimal.ONE);
        dataOrder.setTotalAmount(CommonProductTO.getUnitPrice().multiply(BigDecimal.ONE));
        orderRepository.save(dataOrder);
        logger.info("------> [Order 下单完成 : {}] <-------", dataOrder.getOrderId());

        String accountMsg = accountClient.buyProduct(dataOrder.getOrderId());
        String productMsg = productClient.buyProduct(dataOrder.getOrderId());

        responseMsg = "交易完成 , 下单单号 :" + dataOrder.getOrderId() + " !" + accountMsg + "-" + productMsg;
//        }
        return responseMsg;
    }


    public void confirmMethod(String accountId, Integer productId) {
        logger.info("------> [执行 Confirm] <-------");
    }

    public void cancelMethod(String accountId, Integer productId) {
        logger.info("------> [执行 Cancel] <-------");
    }

}
