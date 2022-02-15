package com.myjpa.demo.controller;

import com.myjpa.demo.entity.UserEntity;
import com.myjpa.demo.service.SaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname SaveController
 * @Description TODO
 * @Date 2020/8/14 17:20
 * @Created by zengzg
 */
@RestController
@RequestMapping("/save")
public class SaveController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SaveService saveService;

    @GetMapping("/one")
    public void testSave() {
        logger.info("------> this is in saveOne <-------");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("gang");
        userEntity.setOrgid("1");

        saveService.saveStep1(userEntity);
//        saveService.saveStep2(userEntity);

    }


    @GetMapping("/performance")
    public void performance() {
        logger.info("------> this is in saveOne <-------");
        saveService.updateTest();
    }

}
