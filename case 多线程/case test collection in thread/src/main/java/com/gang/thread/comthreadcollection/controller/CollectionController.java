package com.gang.thread.comthreadcollection.controller;

import com.gang.thread.comthreadcollection.service.CollectionService;
import com.gang.thread.comthreadcollection.service.CollectionTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname CollectionController
 * @Description TODO
 * @Date 2020/11/25 11:07
 * @Created by zengzg
 */
@RequestMapping("/collection")
@RestController
public class CollectionController {

    @Autowired
    private CollectionTest collectionTest;

    @GetMapping
    public void test() {
        collectionTest.test1();
    }
}
