package com.gang.sping.remote.demo.service;

import org.springframework.stereotype.Service;

/**
 * @Classname TestService
 * @Description TODO
 * @Date 2020/10/31 17:08
 * @Created by zengzg
 */
@Service
public class TestService implements ITestService {

    public String get() {
        return "success";
    }
}
