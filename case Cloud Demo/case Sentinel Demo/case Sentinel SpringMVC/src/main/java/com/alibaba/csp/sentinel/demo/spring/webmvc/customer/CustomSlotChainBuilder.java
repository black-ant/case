package com.alibaba.csp.sentinel.demo.spring.webmvc.customer;

import com.alibaba.csp.sentinel.slotchain.ProcessorSlotChain;
import com.alibaba.csp.sentinel.slotchain.SlotChainBuilder;
import com.alibaba.csp.sentinel.slots.DefaultSlotChainBuilder;
import com.alibaba.csp.sentinel.spi.Spi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomSlotChainBuilder implements SlotChainBuilder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ProcessorSlotChain build() {
        logger.info("添加自定义限流链路");
        ProcessorSlotChain chain = new DefaultSlotChainBuilder().build();
        chain.addLast(new TestAnalyseSlot());
//        chain.addLast(new DegradeEarlyWarningSlot());
        return chain;
    }
}
