package com.gang.elastic.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.common.elastic.logic.ElasticConnect;
import com.gang.common.elastic.model.BaseExampleDocument;
import com.gang.common.elastic.repository.BaseDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname TestLogic
 * @Description TODO
 * @Date 2020/4/1 21:30
 * @Created by zengzg
 */
@RestController
@RequestMapping("/test")
public class TestLogic {

    @Autowired
    private BaseDocumentRepository repository;

    @GetMapping("/get")
    public String get() {
        BaseExampleDocument baseExampleDocument = new BaseExampleDocument();
        baseExampleDocument.setBrand("1111");
        baseExampleDocument.setTitle("2222");
        repository.save(baseExampleDocument);
        return JSONObject.toJSONString(baseExampleDocument);
    }

}
