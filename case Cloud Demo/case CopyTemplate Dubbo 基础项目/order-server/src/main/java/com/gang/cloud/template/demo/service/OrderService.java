package com.gang.cloud.template.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.gang.cloud.template.api.OrderClient;
import com.gang.cloud.template.api.ProductClient;
import com.gang.cloud.template.demo.entity.OrderEntity;
import com.gang.cloud.template.demo.repository.OrderRepository;
import com.gang.cloud.template.to.CommonOrderTO;
import com.gang.cloud.template.to.CommonOrderTO;
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
public class OrderService implements OrderClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderRepository OrderRepository;

    @Reference(lazy = true, check = false)
    private OrderClient orderClient;

    /**
     * 返回 List 集合
     *
     * @return
     */
    public List<CommonOrderTO> list() {
        List<OrderEntity> productSearch = OrderRepository.findAll();
        List<CommonOrderTO> response = new ArrayList<>();
        productSearch.stream().map(item -> {
            CommonOrderTO productTO = new CommonOrderTO();
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
    public CommonOrderTO getOne(Integer id) {
        OrderEntity productTO = OrderRepository.findById(id).orElse(new OrderEntity());
        CommonOrderTO responseTO = new CommonOrderTO();
        BeanUtils.copyProperties(productTO, responseTO);
        return responseTO;
    }

    public String buyProduct(Integer orderId) {
        CommonOrderTO CommonOrderTO = orderClient.getOne(orderId);
        logger.info("------> ProductController : 订单查询成功 :{} <-------", JSONObject.toJSONString(CommonOrderTO));
        return "success";
    }
}
