package com.gang.study.elasticsearch.demo.controller;

import com.gang.study.elasticsearch.demo.repository.AOrderRepository;
import com.gang.study.elasticsearch.demo.service.SearchService;
import com.gang.study.elasticsearch.demo.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Classname OrderController
 * @Description TODO
 * @Date 2022/3/7
 * @Created by zengzg
 */
@RestController
@RequestMapping("/test")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TestService testService;
    @Resource
    private AOrderRepository aorderRepository;
    @Resource
    private SearchService searchService;

    @GetMapping("add")
    public void add() {
        logger.info("------> add <-------");
        testService.add();
    }

    @GetMapping("filter")
    public void filter() {

    }

    @GetMapping("highLigth")
    public void highLigth() throws Exception {
        searchService.hightQuery();
    }

    @GetMapping("sync")
    public void sync() throws Exception {
        searchService.sync();
    }
}
