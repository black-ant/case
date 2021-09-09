package com.gang.cloud.template.demo.config;

import com.gang.cloud.template.demo.client.ProductFeignClient;
import com.gang.cloud.template.to.CommonProductTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Classname ProductServiceFallBack
 * @Description TODO
 * @Date 2021/9/8
 * @Created by zengzg
 */
@Component
public class ProductServiceFallBack implements ProductFeignClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Collection<CommonProductTO> list() {
        return null;
    }

    @Override
    public CommonProductTO getById(String id) {
        return null;
    }

    @Override
    public String buyProduct(String orderId) {
        return null;
    }

    @Override
    public String test() {
        logger.info("------> 远程调用异常 <-------");
        return "远程调用异常";
    }

    @Override
    public String test002() {
        return null;
    }
}

