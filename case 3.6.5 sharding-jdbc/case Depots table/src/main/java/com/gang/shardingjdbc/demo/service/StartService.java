package com.gang.shardingjdbc.demo.service;

import com.gang.shardingjdbc.demo.repository.SelfAddressRepository;
import com.gang.shardingjdbc.demo.service.api.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/4/26
 * @Created by zengzg
 */
@Component
public class StartService {

    @Autowired
    private OrderServiceImpl orderService;


    public void run() throws SQLException {
        try {
//            orderService.initEnvironment();
            orderService.processSuccess();
        } finally {
//            orderService.cleanEnvironment();
        }
    }

    public void runFailure() throws SQLException {
        try {
            orderService.initEnvironment();
            orderService.processFailure();
        } finally {
            orderService.cleanEnvironment();
        }
    }
}
