package com.gang.study.source.springboot.demo.common;

import com.gang.study.source.springboot.demo.circular.BeanAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Classname AutowiredService
 * @Description TODO
 * @Date 2021/5/19
 * @Created by zengzg
 */
@Component
public class AutowiredService {

    //    @Autowired
    private CommonService commonService;
    //    @Autowired
    private BeanAService beanAService;

    @Value("${gang.test.name}")
    private String name;

    private String username;

    private Integer age;

//    @Autowired
//    public AutowiredService(CommonService commonService, BeanAService beanAService) {
//        this.commonService = commonService;
//        this.beanAService = beanAService;
//    }

    public void test() {
        commonService.showInfo();
        beanAService.getInfo();
    }

    public CommonService getCommonService() {
        return commonService;
    }

    @Autowired
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    public BeanAService getBeanAService() {
        return beanAService;
    }

    @Autowired
    public void setBeanAService(BeanAService beanAService) {
        this.beanAService = beanAService;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
