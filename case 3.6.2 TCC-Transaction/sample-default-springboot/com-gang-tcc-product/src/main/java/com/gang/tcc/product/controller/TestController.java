package com.gang.tcc.product.controller;

import com.gang.tcc.order.service.TccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/5/28
 * @Created by zengzg
 */
@RestController
public class TestController {

    @Autowired
    private TccService tccService;

    @GetMapping("product")
    @ResponseBody
    public String tcc(String from, String to, BigDecimal amount) throws Exception {
        try {
            tccService.doTry(null, from, to, amount);
            return "success";
        } catch (Exception e) {
            return "fail：" + e.getMessage();
        } finally {
            System.out.println("success finally");
        }
    }
}
