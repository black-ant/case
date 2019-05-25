package net.gang.bundles.demo.service;

import org.identityconnectors.framework.spi.Configuration;
import org.identityconnectors.framework.spi.Connector;
import org.springframework.stereotype.Service;

@Service
public class ConnectorFacadeProxy implements Connector {

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public void init(Configuration configuration) {

    }

    @Override
    public void dispose() {

    }
}
