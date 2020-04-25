package com.gang.study.sofabbot.server.controller;

import com.alipay.sofa.runtime.api.aware.ClientFactoryAware;
import com.alipay.sofa.runtime.api.client.ClientFactory;
import com.alipay.sofa.runtime.api.client.ReferenceClient;
import com.alipay.sofa.runtime.api.client.param.ReferenceParam;
import com.gang.study.sofaboot.api.config.APIService;
import com.gang.study.sofaboot.api.config.APIServiceImpl;
import org.springframework.stereotype.Component;

/**
 * @Classname APIConsumer
 * @Description TODO
 * @Date 2020/4/25 23:20
 * @Created by zengzg
 */
@Component
public class APIConsumer implements ClientFactoryAware {

    private ClientFactory clientFactory;

    public void init() {
        ReferenceClient referenceClient = clientFactory.getClient(ReferenceClient.class);
        ReferenceParam<APIServiceImpl> referenceParam = new ReferenceParam<>();
        referenceParam.setInterfaceType(APIServiceImpl.class);
        referenceParam.setUniqueId("apiServiceImpl");
        APIService apiService = referenceClient.reference(referenceParam);
        apiService.send();
    }

    @Override
    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
}
