package com.gang.cloud.template.demo.client;

import com.gang.cloud.template.to.CommonOrderTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

/**
 * @Classname SampleFeignClient
 * @Description TODO
 * @Date 2021/3/4 21:21
 * @Created by zengzg
 */
@FeignClient("order-server")
@Component
public interface OrderFeignClient {

    @GetMapping("/order/list")
    Collection<CommonOrderTO> list();

    @GetMapping("/order/get/{id}")
    CommonOrderTO getById(@PathVariable("id") String id);

}
