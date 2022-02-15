package com.saga.clienta.controller;

import com.saga.clienta.entity.HistoryEntity;
import com.saga.clienta.repository.HistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname HistoryController
 * @Description TODO
 * @Date 2021/11/14
 * @Created by zengzg
 */
@RestController
public class HistoryController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HistoryRepository repository;

    @GetMapping("/get")
    public List<HistoryEntity> get() {
        logger.info("------> this is in get <-------");
        return repository.findAll();
    }
}
