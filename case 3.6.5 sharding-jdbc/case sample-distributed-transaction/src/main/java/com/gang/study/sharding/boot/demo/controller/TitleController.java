package com.gang.study.sharding.boot.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.sharding.boot.demo.dao.BlogDao;
import com.gang.study.sharding.boot.demo.dao.TitleDao;
import com.gang.study.sharding.boot.demo.entity.BlogEntity;
import com.gang.study.sharding.boot.demo.entity.TitleEntity;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Classname TestController
 * @Description TODO
 * @Date 2021/5/5
 * @Created by zengzg
 */
@RestController
@RequestMapping("/title")
public class TitleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TitleDao titleDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/test/insert")
    public String test3(HttpServletRequest request) {
        logger.info("------> /test/insert <-------");
        for (int i = 0; i < 10; i++) {
            TitleEntity titleEntity = new TitleEntity();
            Integer columnId = new Random().nextInt(999);
            Integer titleId = new Random().nextInt(999);
            titleEntity.setTitle("标题 :" + ((titleId % 2 == 1) ? "表二" : "表一"));
            titleEntity.setSplitId(titleId);
            titleEntity.setTitleName("作者 :" + ((columnId % 2 == 1) ? "数据库二" : "数据库一"));
            titleEntity.setColumnId(columnId);
            titleDao.save(titleEntity);
            logger.info("------> 插入完成 :[{}] <-------", JSONObject.toJSONString(titleEntity));
        }

        return "success";
    }

    @RequestMapping("/test/select")
    public List<TitleEntity> select(HttpServletRequest request) {
        logger.info("------> /test/select <-------");
        List<TitleEntity> entities = titleDao.findAll();
        entities.forEach(item -> {
            logger.info("------> 查询完成 :[{}] <-------", JSONObject.toJSONString(item));
        });
        return entities;
    }
}
