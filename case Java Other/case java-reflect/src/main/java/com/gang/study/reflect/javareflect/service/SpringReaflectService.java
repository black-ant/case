package com.gang.study.reflect.javareflect.service;

import com.gang.study.reflect.javareflect.base.BaseInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Classname SpringReaflectService
 * @Description TODO
 * @Date 2020/11/12 13:51
 * @Created by zengzg
 */
@Component
public class SpringReaflectService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> SpringReaflectService <-------");
        springReflect();
    }

    /**
     * 通过 Spring 的反射对象获取
     * <p>
     * 注 : 该对象必须放入 Spring 的 容器管理之中
     */
    public void springReflect() {
        Map<String, BaseInterface> beans = context.getBeansOfType(BaseInterface.class);
        beans.keySet().forEach(item -> {
            logger.info("------> this is item :{} -- value :{} <-------", item,
                    beans.get(item).getClass().getSimpleName());
        });
    }
}
