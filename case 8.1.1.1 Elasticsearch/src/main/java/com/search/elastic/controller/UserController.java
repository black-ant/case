package com.search.elastic.controller;

import com.search.elastic.entiry.SampleEntity;
import com.search.elastic.entiry.UserEntity;
import com.search.elastic.repository.SampleRepository;
import com.search.elastic.repository.UserRepository;
import com.search.elastic.to.ResponseTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2021/7/16
 * @Created by zengzg
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    private static List<String> ENUM_TYPE = Arrays.asList("AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH", "II");

    /**
     * 127.0.0.1:8086/sample/add/successUser
     *
     * @param data
     * @return
     */
    @GetMapping("/add/{data}")
    public ResponseTO<UserEntity> addSampleData(@PathVariable("data") String data) {

        logger.info("------> [进入 Data 创建逻辑] <-------");

        UserEntity sample = new UserEntity();
        sample.setUserName(data);
        sample.setUserInfo(data + "title");
        return ResponseTO.commonResponse(userRepository.save(sample));
    }

    /**
     * 127.0.0.1:8086/sample/addList/successUser
     *
     * @param data
     * @return
     */
    @GetMapping("/addList/{data}")
    public ResponseTO<String> addSampleDataList(@PathVariable("data") String data) {

        logger.info("------> [进入 Data 创建逻辑] <-------");

        for (int i = 0; i < 1000; i++) {
            UserEntity sample = new UserEntity();
            sample.setUserName(data + new Random().nextInt(999999999));
            sample.setUserInfo(ENUM_TYPE.get(new Random().nextInt(ENUM_TYPE.size())));
            logger.info("------> 创建 index-{} : [{}] <-------", sample.getUserName());
            userRepository.save(sample);
        }
        return ResponseTO.commonResponse("success");
    }


    @GetMapping("/getAll")
    public ResponseTO<List<UserEntity>> getSampleDataList() {
        Iterable<UserEntity> iterable = userRepository.findAll();

        List<UserEntity> sampleEntities = new ArrayList<>();
        iterable.forEach(item -> {
            sampleEntities.add(item);
        });
        return ResponseTO.commonResponse(sampleEntities);
    }
}
