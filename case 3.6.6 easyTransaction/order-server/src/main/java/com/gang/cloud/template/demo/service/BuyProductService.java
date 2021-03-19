package com.gang.cloud.template.demo.service;

import com.gang.cloud.template.demo.repository.NoDataOrderRepository;
import com.gang.cloud.template.to.CommonOrderTO;
import com.gang.cloud.template.to.CommonProductTO;
import com.gang.cloud.template.to.CommonResponseTO;
import com.yiqiniu.easytrans.core.EasyTransFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.Future;

/**
 * @Classname BuySingleProduct
 * @Description TODO
 * @Created by zengzg
 */
@Service
public class BuyProductService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String BUSINESS_CODE = "buySth";

    @Autowired
    private NoDataOrderRepository orderRepository;

    @Autowired
    private EasyTransFacade transaction;

    @Transactional
    public String doEasyTransaction(String accountId, String productId) throws Exception {

        String responseMsg = "购买中...";
        CommonProductTO productTO = new CommonProductTO();

        if (productTO == null) {
            responseMsg = "产品不存在";
        } else if (productTO.getProductNum().compareTo(BigDecimal.ONE) < 0) {
            responseMsg = "产品库存不足";
        } else {

            // TCC 本地事务处理 : 生成订单
            CommonOrderTO dataOrder = new CommonOrderTO();
            dataOrder.setAccountId(accountId);
            dataOrder.setProductId(productId);
            dataOrder.setProductNum(BigDecimal.ONE);
            dataOrder.setTotalAmount(productTO.getUnitPrice().multiply(BigDecimal.ONE));
            orderRepository.addOrder(dataOrder);

            // TCC Step 1 : 本地订单完成后获取订单 ID
            Integer id = dataOrder.getOrderId();
            logger.info("------> [Order 下单完成 : {}] <-------", dataOrder.getOrderId());

            // TODO PRO : 订单 ID 必须要 Int
            // Tcc Step 2 : 通过订单ID生成对应事务
            transaction.startEasyTrans(BUSINESS_CODE, id);

            // TCC Step 3 : 请求远程
            @SuppressWarnings("unused")
            Future<CommonResponseTO> deductFuture = transaction.execute(dataOrder);

            // TCC Step 4 : 远程处理
            CommonResponseTO commonResponseTO = deductFuture.get();
            logger.info("------> 处理完成 :{} <-------", commonResponseTO.getStatus());

            responseMsg = "交易完成 +  " + commonResponseTO.getMsg();
        }
        return responseMsg;
    }


}
