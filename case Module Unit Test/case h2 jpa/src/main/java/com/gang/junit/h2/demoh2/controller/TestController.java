package com.gang.junit.h2.demoh2.controller;

import com.gang.junit.h2.demoh2.entity.TestEntitiy;
import com.gang.junit.h2.demoh2.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2020/10/12 17:34
 * @Created by zengzg
 */

@RestController
public class TestController {

    @Autowired
    private TestRepository userRepository;

    @GetMapping("/user/{id}")
    public TestEntitiy findById(@PathVariable String id) {
        return this.userRepository.getOne(id);
    }

}
