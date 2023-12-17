package com.alibaba.csp.sentinel.demo.spring.webmvc.customer;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.slotchain.AbstractLinkedProcessorSlot;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.spi.Spi;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestAnalyseSlot  extends AbstractLinkedProcessorSlot<DefaultNode> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void entry(Context context, ResourceWrapper resourceWrapper, DefaultNode param, int count, boolean prioritized, Object... args) throws Throwable {
        logger.info("成功进入分析代码");
        logger.info("ResourceWrapper :{}", JSONObject.toJSONString(resourceWrapper));
        logger.info("DefaultNode :{}", JSONObject.toJSONString(param));
        logger.info("count :{}", count);
        logger.info("Current context:{} ", context.getName());
        logger.info("Current entry resource: {}", context.getCurEntry().getResourceWrapper().getName());
        fireEntry(context, resourceWrapper, param, count, prioritized, args);
    }

    @Override
    public void exit(Context context, ResourceWrapper resourceWrapper, int count, Object... args) {
    }
}
