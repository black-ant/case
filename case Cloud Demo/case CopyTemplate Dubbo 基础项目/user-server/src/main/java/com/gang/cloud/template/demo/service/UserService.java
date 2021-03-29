package com.gang.cloud.template.demo.service;

import com.gang.cloud.template.api.OrderClient;
import com.gang.cloud.template.api.UserClient;
import com.gang.cloud.template.demo.entity.UserEntity;
import com.gang.cloud.template.demo.repository.UserRepository;
import com.gang.cloud.template.to.CommonUserTO;
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
public class UserService implements UserClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Reference(lazy = true, check = false)
    private OrderClient orderClient;

    /**
     * 返回 List 集合
     *
     * @return
     */
    public List<CommonUserTO> list() {
        List<UserEntity> productSearch = userRepository.findAll();
        List<CommonUserTO> response = new ArrayList<>();
        productSearch.stream().map(item -> {
            CommonUserTO productTO = new CommonUserTO();
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
    public CommonUserTO getOne(String id) {
        UserEntity productTO = userRepository.getOne(id);
        CommonUserTO responseTO = new CommonUserTO();
        BeanUtils.copyProperties(productTO, responseTO);
        return responseTO;
    }

    public String buyProduct(String orderId) {
//        CommonUserTO CommonUserTO = userRepository.getOne(orderId);
//        logger.info("------> ProductController : 订单查询成功 :{} <-------", JSONObject.toJSONString(CommonUserTO));
        return "success";
    }
}
