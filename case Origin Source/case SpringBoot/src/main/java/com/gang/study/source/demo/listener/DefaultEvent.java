package com.gang.study.source.demo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

/**
 * @Classname MySelfEvent
 * @Description TODO
 * @Date 2021/5/24
 * @Created by zengzg
 */
public class DefaultEvent extends ApplicationEvent {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param tranTO the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public DefaultEvent(ListenerTranTO tranTO) {
        super(tranTO);
    }


}
