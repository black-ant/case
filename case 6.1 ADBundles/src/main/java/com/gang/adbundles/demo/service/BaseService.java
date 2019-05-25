package com.gang.adbundles.demo.service;

import com.gang.adbundles.demo.ADConnectorImpl;
import com.gang.adbundles.demo.config.AbstractADContext;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/25 20:15
 * @Version 1.0
 **/
@Data
public abstract class BaseService extends AbstractADContext {

    @Autowired
    private ADConnectorImpl adConnector;


}
