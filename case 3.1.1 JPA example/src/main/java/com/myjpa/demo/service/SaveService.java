package com.myjpa.demo.service;

import com.myjpa.demo.entity.UserEntity;
import com.myjpa.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * @Classname SaveService
 * @Description TODO
 * @Date 2020/8/14 17:22
 * @Created by zengzg
 */
@Service
public class SaveService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    public void saveStep1(UserEntity userEntity) {
        userEntity.setRemark("step1 ");
        userRepository.save(userEntity);
    }

    public void saveStep2(UserEntity userEntity) {
        userEntity.setUsertype("step2");
        userRepository.save(userEntity);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateTest(int first) {
        List<UserEntity> userEntities = new LinkedList<>();

        for (int i = 0; i < 1000; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserid(first + i);
            userEntity.setUsername("gang" + i);
            userRepository.save(userEntity);
            userEntities.add(userEntity);
//            logger.info("------> creaate User :{} <-------", userEntity.getUsername());
        }

//        logger.info("------> 开始 Update 更新 <-------");
//        Long startTime1 = System.currentTimeMillis();
//        logger.info("------> start time :{} <-------", startTime1);
        userEntities.forEach(item -> {
            item.setUserlink("1");
            userRepository.save(item);
        });
//        Long endTime1 = System.currentTimeMillis();
//        logger.info("------> end time :{} <-------", endTime1);
//        logger.info("------> 结束 Update 更新 :{}<-------", endTime1 - startTime1);

//        logger.info("------> 开始 Search - Update 更新 <-------");
//        Long startTime2 = System.currentTimeMillis();
//        logger.info("------> start time :{} <-------", startTime2);
        userEntities.forEach(item -> {
            UserEntity entity = userRepository.getOne(item.getUserid());
            entity.setRemark("2");
            userRepository.save(entity);
        });
//        Long endTime2 = System.currentTimeMillis();
//        logger.info("------> end time :{} <-------", endTime2);
//        logger.info("------> 结束 Search - Update  更新 :{}<-------", endTime2 - startTime2);


//        logger.info("------> 开始 Query 更新 <-------");
//        Long startTime3 = System.currentTimeMillis();
//        logger.info("------> start time :{} <-------", startTime3);
        userEntities.forEach(item -> {
            userRepository.updateUser("3", item.getUserid());
        });
        Long endTime3 = System.currentTimeMillis();
//        logger.info("------> end time :{} <-------", endTime3);
//        logger.info("------> 结束 Query  更新 :{}<-------", endTime3 - startTime3);
        logger.info("------> end time :{} <-------", endTime3);
    }
}
