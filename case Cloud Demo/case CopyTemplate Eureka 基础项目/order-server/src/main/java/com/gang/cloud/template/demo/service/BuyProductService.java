package com.gang.cloud.template.demo.service;

import com.gang.cloud.template.demo.client.AccountFeignClient;
import com.gang.cloud.template.demo.client.ProductFeignClient;
import com.gang.cloud.template.demo.entity.NoDataOrder;
import com.gang.cloud.template.demo.repository.NoDataOrderRepository;
import com.gang.cloud.template.to.CommonAccountTO;
import com.gang.cloud.template.to.CommonProductTO;
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
    private NoDataOrderRepository orderRepository;

    @Autowired
    private AccountFeignClient accountFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;


    public String buySingleProduct(String accountId, String productId) {

        String responseMsg = "购买中...";
        CommonProductTO CommonProductTO = productFeignClient.getById(productId);

        if (CommonProductTO == null) {
            responseMsg = "产品不存在";
        } else if (CommonProductTO.getProductNum().compareTo(BigDecimal.ONE) < 0) {
            responseMsg = "产品库存不足";
        } else {
            NoDataOrder dataOrder = new NoDataOrder();
            dataOrder.setAccountId(accountId);
            dataOrder.setProductId(productId);
            dataOrder.setProductNum(BigDecimal.ONE);
            dataOrder.setTotalAmount(CommonProductTO.getUnitPrice().multiply(BigDecimal.ONE));
            orderRepository.addOrder(dataOrder);
            logger.info("------> [Order 下单完成 : {}] <-------", dataOrder.getOrderId());

            accountFeignClient.buyProduct(dataOrder.getOrderId());
            productFeignClient.buyProduct(dataOrder.getOrderId());

            CommonAccountTO CommonAccountTO = accountFeignClient.getById("1");
            CommonProductTO productTONew = productFeignClient.getById("1");
            responseMsg = "交易完成 ! [账户余额 :{} " + CommonAccountTO.getAccountAssets().intValue() + "]" + " [ 货品余额 :{} " + productTONew.getProductNum().intValue() + "]";
        }
        return responseMsg;
    }

    public String buyMultipleProduct() {
        return "";
    }
}
