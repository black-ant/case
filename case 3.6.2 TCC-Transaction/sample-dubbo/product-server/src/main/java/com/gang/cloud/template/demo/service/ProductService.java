package com.gang.cloud.template.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.api.OrderClient;
import com.gang.cloud.template.api.ProductClient;
import com.gang.cloud.template.demo.entity.ProductEntity;
import com.gang.cloud.template.demo.repository.ProductRepository;
import com.gang.cloud.template.exception.OrderException;
import com.gang.cloud.template.to.CommonOrderTO;
import com.gang.cloud.template.to.CommonProductTO;
import com.gang.cloud.template.transaction.DefaultDubboTransactionContextEditor;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.dubbo.context.DubboTransactionContextEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ProductService
 * @Description TODO
 * @Date 2021/3/22
 * @Created by zengzg
 */
@Component
@Service
public class ProductService implements ProductClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @InitBinder
    public void init() {
        logger.info("------> ProductService init() <-------");
    }

    @Autowired
    private ProductRepository productRepository;

    @Reference(lazy = true, check = false)
    private OrderClient orderClient;

    /**
     * 返回 List 集合
     *
     * @return
     */
    public List<CommonProductTO> list() {
        List<ProductEntity> productSearch = productRepository.findAll();
        List<CommonProductTO> response = new ArrayList<>();
        productSearch.stream().map(item -> {
            CommonProductTO productTO = new CommonProductTO();
            BeanUtils.copyProperties(item, productTO);
            return productTO;
        }).forEach(item -> response.add(item));
        return response;
    }

    /**
     * 返回单个集合c
     *
     * @param id
     * @return
     */
    public CommonProductTO getOne(Integer id) {
        ProductEntity productTO = productRepository.findById(id).orElse(new ProductEntity());
        CommonProductTO responseTO = new CommonProductTO();
        BeanUtils.copyProperties(productTO, responseTO);
        return responseTO;
    }


    @Transactional
    public void confirmRecord(Integer tradeOrderDto) {
        logger.info("------> AccountController confirmRecord :{} <-------", tradeOrderDto);
    }

    @Transactional
    public void cancelRecord(Integer tradeOrderDto) {
        logger.info("------> AccountController cancelRecord :{} <-------", tradeOrderDto);
    }

    @Compensable(confirmMethod = "confirmRecord", cancelMethod = "cancelRecord", transactionContextEditor = DefaultDubboTransactionContextEditor.class)
    public String buyProduct(Integer orderId) {
        CommonOrderTO orderTO = orderClient.getOne(orderId);
        logger.info("------> ProductController : 订单查询成功 :{} <-------", JSONObject.toJSONString(orderTO));
        ProductEntity productTO = productRepository.findById(Integer.valueOf(orderTO.getProductId())).orElse(new ProductEntity());

        if (productTO.getProductNum().compareTo(orderTO.getProductNum()) > 0) {
            productTO.setProductNum(productTO.getProductNum().subtract(orderTO.getProductNum()));
        } else {
            throw new OrderException();
        }
        productRepository.save(productTO);
        return "[产品消费完成 , 剩余 [" + productTO.getProductNum() + "] ";
    }
}
