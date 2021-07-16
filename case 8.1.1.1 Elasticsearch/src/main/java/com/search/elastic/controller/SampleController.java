package com.search.elastic.controller;

import com.search.elastic.entiry.SampleEntity;
import com.search.elastic.repository.SampleRepository;
import com.search.elastic.to.ResponseTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Classname SampleController
 * @Description
 * @Date 2021/7/15
 * @Created by zengzg
 */
@RestController
@RequestMapping("/sample")
public class SampleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SampleRepository sampleRepository;

    /**
     * 127.0.0.1:8086/sample/get
     *
     * @param key
     * @return
     */
    @GetMapping("/get/{key}")
    public ResponseTO<SampleEntity> getSampleData(@PathVariable("key") Integer key) {
        return ResponseTO.commonResponse(sampleRepository.findById(key).get());
    }

    /**
     * 127.0.0.1:8086/sample/getAll
     * @return
     */
    @GetMapping("/getAll")
    public ResponseTO<List<SampleEntity>> getSampleDataList() {
        Iterable<SampleEntity> iterable = sampleRepository.findAll();

        List<SampleEntity> sampleEntities = new ArrayList<>();
        iterable.forEach(item -> {
            sampleEntities.add(item);
        });
        return ResponseTO.commonResponse(sampleEntities);
    }

    /**
     * 127.0.0.1:8086/sample/add/successUser
     *
     * @param data
     * @return
     */
    @GetMapping("/add/{data}")
    public ResponseTO<SampleEntity> addSampleData(@PathVariable("data") String data) {

        logger.info("------> [进入 Data 创建逻辑] <-------");

        SampleEntity sample = new SampleEntity();
        sample.setInfoTitle(data);
        sample.setInfoDetails(data + "title");
        return ResponseTO.commonResponse(sampleRepository.save(sample));
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
            SampleEntity sample = new SampleEntity();
            sample.setInfoTitle(data + new Random().nextInt(999999999));
            sample.setInfoDetails(data + "title:" + i);
            logger.info("------> 创建 [{}] <-------", sample.getInfoTitle());
            sampleRepository.save(sample);
        }
        return ResponseTO.commonResponse("success");


    }


}
