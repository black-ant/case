package com.gang.spring.beanmanager.demo.controller;

import com.gang.spring.beanmanager.demo.delay.DelayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname DelayController
 * @Description TODO
 * @Date 2021/2/18 9:43
 * @Created by zengzg
 */
@RestController
@RequestMapping("/delay")
public class DelayController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DefaultListableBeanFactory autowireCapableBeanFactory;
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired(required = false)
    private DelayService delayService;


    @GetMapping("/test")
    public String doTest() {
        registryBean();
        logger.info("------> this is in delay do test :{} <-------", delayService);
        DelayService registryBean = applicationContext.getBean("DelayService", DelayService.class);
        logger.info("------> this is in delay registryBean :{} <-------", registryBean, registryBean.getName());
        return "success";
    }

    public void registryBean() {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DelayService.class);
        beanDefinitionBuilder.addConstructorArgValue("black");
        String beanName = "DelayService";
        //默认单
        beanDefinitionBuilder.setScope("prototype");
        autowireCapableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
    }

}
