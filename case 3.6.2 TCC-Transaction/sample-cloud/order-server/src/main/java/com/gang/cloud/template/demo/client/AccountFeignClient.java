package com.gang.cloud.template.demo.client;

import com.gang.cloud.template.demo.entity.CloudTemplateEntity;
import com.gang.cloud.template.demo.entity.api.ICloudTemplateEntity;
import com.gang.cloud.template.demo.to.AccountTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * @Classname SampleFeignClient
 * @Description TODO
 * @Date 2021/3/4 21:21
 * @Created by zengzg
 */
@FeignClient("account-server")
@Component
public interface AccountFeignClient {

    @GetMapping("/template/get")
    CloudTemplateEntity get(@RequestParam("desc") String desc);


    @GetMapping("/account/list")
    Collection<AccountTO> list();

    @GetMapping("/account/get/{id}")
    AccountTO getById(@PathVariable("id") String id);

    @GetMapping("/account/buy/{orderId}")
    String buyProduct(@PathVariable("orderId") String orderId);


}
