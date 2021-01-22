package com.gang.study.webflow.demo.conversion;

import org.springframework.stereotype.Component;

/**
 *
 * @author Jerome Leleu
 * @since 4.0.0
 */
@Component
public class DefaultConversionService extends org.springframework.binding.convert.service.DefaultConversionService {

    /**
     */
    public DefaultConversionService() {
        super();
        addConverter(new CompositeFlowExecutionKeyConverter());
    }
}
