package com.gang.cloud.template.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.api.OrderClient;
import com.gang.cloud.template.api.ProductClient;
import com.gang.cloud.template.demo.entity.ProductEntity;
import com.gang.cloud.template.demo.repository.ProductRepository;
import com.gang.cloud.template.to.CommonOrderTO;
import com.gang.cloud.template.to.CommonProductTO;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public String buyProduct(Integer orderId) {
        CommonOrderTO orderTO = orderClient.getOne(orderId);
        logger.info("------> ProductController : 订单查询成功 :{} <-------", JSONObject.toJSONString(orderTO));
        ProductEntity productTO = productRepository.findById(Integer.valueOf(orderTO.getProductId())).orElse(new ProductEntity());
        productTO.setProductNum(productTO.getProductNum().subtract(orderTO.getProductNum()));
        productRepository.save(productTO);
        return "[产品消费完成 , 剩余 [" + productTO.getProductNum() + "] ";
    }
}
