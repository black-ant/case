package net.gang.bundles.demo.service;


import org.identityconnectors.framework.api.ConnectorFacade;
import org.identityconnectors.framework.common.objects.Attribute;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptions;
import org.identityconnectors.framework.common.objects.Uid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.Future;

@Service
public class StartBundles {

    @Autowired
    ConnectorFacadeProxy connectorFacadeProxy;

    public void initConnector(){
        connectorFacadeProxy.init(connectorFacadeProxy.getConfiguration());
    }

    @Async
    public Future<Uid> create(final ConnectorFacade connector, final ObjectClass objectClass, final Set<Attribute> attrs, final OperationOptions options) {
        return new AsyncResult<>(connector.create(objectClass, attrs, options));
    }
}
