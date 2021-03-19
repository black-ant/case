package com.gang.study.rmi.demo.invoke.api;

import java.math.BigDecimal;

/**
 * @Classname CapitalAccountService
 * @Description RMI 接口
 * @Date 2021/3/17
 * @Created by zengzg
 */
public interface CapitalAccountService {

    BigDecimal getCapitalAccountByUserId(long userId);
}
