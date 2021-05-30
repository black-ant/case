package com.myjpa.demo.service;

import com.myjpa.demo.entity.OrgEntity;
import com.myjpa.demo.entity.UserEntity;
import com.myjpa.demo.repository.OrgRepository;
import com.myjpa.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Random;

/**
 * @Classname TransactionService
 * @Description TODO
 * @Date 2021/5/17
 * @Created by zengzg
 */
@Component
public class TransactionService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrgRepository orgRepository;

    /**
     * 执行事务得
     *
     * @return
     */
    @org.springframework.transaction.annotation.Transactional
    public String doExceptionTrans() {
        logger.info("------> this is in doExceptionTrans <-------");

        for (int i = 0; i < 1; i++) {
            userRepository.save(buildUserEntity());
            logger.info("------> this is in userRepository build <-------");
            if (new Random().nextInt(999) > 800) {
                throw new RuntimeException();
            }
            orgRepository.save(buildOrgEntity());
            logger.info("------> this is in orgRepository build <-------");
        }
        return "success";
    }

    @javax.transaction.Transactional
    public String doExceptionTransOther() {
        logger.info("------> this is in doExceptionTransOther <-------");

        for (int i = 0; i < 1; i++) {
            userRepository.save(buildUserEntity());

            if (new Random().nextInt(999) > 800) {
                throw new RuntimeException();
            }
            orgRepository.save(buildOrgEntity());
        }
        return "success";
    }

    public String doExceptionTransNo() {
        logger.info("------> this is in doExceptionTrans <-------");

        for (int i = 0; i < 1; i++) {
            userRepository.save(buildUserEntity());

            if (new Random().nextInt(999) > 800) {
                throw new RuntimeException();
            }
            orgRepository.save(buildOrgEntity());
        }
        return "success";
    }

    @org.springframework.transaction.annotation.Transactional
    public String doTrans() {
        logger.info("------> this is in doTrans <-------");

        for (int i = 0; i < 5; i++) {
            userRepository.save(buildUserEntity());
            try {
                Thread.sleep(10001);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            orgRepository.save(buildOrgEntity());
        }
        return "success";

    }

    /**
     * 传统得事务管理
     *
     * @return
     */
    public String doTransDefault() {

        try {
            for (int i = 0; i < 10; i++) {
                userRepository.save(buildUserEntity());

                if (new Random().nextInt(999) > 800) {
                    throw new RuntimeException();
                }
                orgRepository.save(buildOrgEntity());
            }
        } catch (Exception e) {

        }
        return "success";
    }


    public UserEntity buildUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserid(new Random().nextInt(9999999));
        userEntity.setUsername("gang :" + userEntity.getUserid());
        return userEntity;
    }


    public OrgEntity buildOrgEntity() {
        OrgEntity orgEntity = new OrgEntity();
        orgEntity.setId(new Random().nextInt(9999999));
        orgEntity.setOrgName("gang :" + orgEntity.getId());
        return orgEntity;
    }

}
