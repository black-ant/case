package com.gang.adbundles.demo.service;

import com.gang.adbundles.demo.ADConnector;
import com.gang.adbundles.demo.AbstractADContext;
import lombok.Data;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/25 20:15
 * @Version 1.0
 **/
@Data
public abstract class BaseService extends AbstractADContext {

//    private ADConnector adConnector;
    public void start(){
        AbstractADContext.init();
    }

}
