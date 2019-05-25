package net.gang.bundles.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;


@Service
public class BundlesOp {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @InitBinder
    public void init() {
        logger.info("server is success start");

    }
}
