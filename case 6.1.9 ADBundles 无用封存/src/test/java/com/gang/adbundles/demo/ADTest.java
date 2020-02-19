package com.gang.adbundles.demo;

import com.gang.adbundles.demo.service.ADService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/26 12:43
 * @Version 1.0
 **/
public class ADTest {

    ADService adService;

    @Before
    public void init(){
        adService = new ADService();
    }

    @Test
    public void testConnector(){
        adService.start();
    }
}
