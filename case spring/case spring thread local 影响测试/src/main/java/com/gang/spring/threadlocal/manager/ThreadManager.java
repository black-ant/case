package com.gang.spring.threadlocal.manager;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.context.request.RequestAttributes;

/**
 * @Classname ThreadManager
 * @Description TODO
 * @Date 2020/12/11 17:32
 * @Created by zengzg
 */
public class ThreadManager {

    private static final ThreadLocal<RequestAttributes> requestAttributesHolder = new NamedThreadLocal("Request attributes");
}
