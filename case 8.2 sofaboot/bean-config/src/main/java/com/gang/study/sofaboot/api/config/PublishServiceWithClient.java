package com.gang.study.sofaboot.api.config;

import com.alipay.sofa.runtime.api.aware.ClientFactoryAware;
import com.alipay.sofa.runtime.api.client.ClientFactory;
import com.alipay.sofa.runtime.api.client.ServiceClient;
import com.alipay.sofa.runtime.api.client.param.ServiceParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Classname APIService
 * @Description TODO
 * @Date 2020/4/25 23:06
 * @Created by zengzg
 */
@Component
public class PublishServiceWithClient implements ClientFactoryAware {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ClientFactory clientFactory;

    public void init() {

        logger.info("------> this is in init <-------");

        ServiceClient serviceClient = clientFactory.getClient(ServiceClient.class);
        ServiceParam serviceParam = new ServiceParam();
        serviceParam.setInstance(new APIServiceImpl(
                "Hello, jvm service service client implementation."));
        serviceParam.setInterfaceType(APIService.class);
        serviceParam.setUniqueId("apiServiceImpl");
        serviceClient.service(serviceParam);
    }

    @Override
    public void setClientFactory(ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
}
