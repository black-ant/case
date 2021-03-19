package com.gang.cloud.template.demo.api;

import com.gang.cloud.template.demo.entity.NoDataAccount;
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


    Collection<NoDataAccount> list();

    NoDataAccount list(@PathVariable("id") String id);

    String buyProduct(@PathVariable("orderId") String orderId);
}
