package com.gang.thread.comthreadcollection.service;

import com.gang.thread.comthreadcollection.api.ItemService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

/**
 * @Classname CollectionService
 * @Description TODO
 * @Date 2020/11/25 10:43
 * @Created by zengzg
 */
@Component
public class CollectionService implements ApplicationContextAware {

    private List<ItemService> services = new LinkedList<>();

    // Spring应用上下文环境
    private static ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        services.add(applicationContext.getBean(ItemOneService.class));
        services.add(applicationContext.getBean(ItemTwoService.class));
        services.add(applicationContext.getBean(ItemOneService.class));
        services.add(applicationContext.getBean(ItemTwoService.class));
        services.add(applicationContext.getBean(ItemOneService.class));
        services.add(applicationContext.getBean(ItemTwoService.class));

    }

    public void run() {
        int nameId = 1;
        for (ItemService service : services) {
            service.test("name" + nameId);
            nameId++;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
