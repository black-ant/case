package com.gang.cloud.template.demo.controller;

import com.gang.cloud.template.demo.entity.ProductEntity;
import com.gang.cloud.template.demo.service.ProductService;
import com.gang.cloud.template.to.CommonProductTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @Classname AccountController
 * @Description TODO
 * @Created by zengzg
 */
@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("list")
    public Collection<CommonProductTO> list() {
        return productService.list();
    }

    @GetMapping("get/{id}")
    public CommonProductTO list(@PathVariable("id") Integer id) {
        return productService.getOne(id);
    }

    @GetMapping("buy/{orderId}")
    public String buyProduct(@PathVariable("orderId") Integer orderId) {
        return productService.buyProduct(orderId);
    }

}
