package com.gang.cloud.template.demo.api;

import com.gang.cloud.template.to.CommonAccountTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

/**
 * @Classname IAccountService
 * @Description TODO
 * @Date 2021/3/17
 * @Created by zengzg
 */
public interface IAccountService {

    @GetMapping("list")
    Collection<CommonAccountTO> list();

    @GetMapping("get/{id}")
    CommonAccountTO list(@PathVariable("id") String id);

    @GetMapping("buy/{orderId}")
    String buyProduct(@PathVariable("orderId") String orderId);
}
