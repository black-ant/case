package com.gang.study.sharding.boot.demo.controller;

import com.gang.study.sharding.boot.demo.dao.BlogDao;
import com.gang.study.sharding.boot.demo.dao.TitleDao;
import com.gang.study.sharding.boot.demo.entity.BlogEntity;
import com.gang.study.sharding.boot.demo.entity.TitleEntity;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

/**
 * @Classname TransController
 * @Description 测试分布式事务处理
 * @Date 2021/5/12
 * @Created by zengzg
 */
@RestController
@RequestMapping("/trans")
public class TransController {

    @Autowired
    TitleDao titleDao;

    @Autowired
    BlogDao blogDao;

    @GetMapping("/test")
    @Transactional
    public String test() {
        for (int i = 0; i < 10; i++) {
            BlogEntity blogEntity = new BlogEntity();
            Integer columnId = new Random().nextInt(999);
            Integer titleId = new Random().nextInt(999);
            blogEntity.setColumnId(columnId);
            blogEntity.setTitle("标题 :" + ((titleId % 2 == 1) ? "表二" : "表一"));
            blogEntity.setTitleId(titleId);
            blogEntity.setAuthor("作者 :" + ((columnId % 2 == 1) ? "数据库二" : "数据库一"));
            blogEntity.setDate(new Date());
            blogDao.save(blogEntity);
            if (columnId > 800) {
                throw new RuntimeException("Error : do Trans");
            }
        }
        return "blogAddSuccess";
    }

    @RequestMapping("/insert")
    @Transactional
    @ShardingTransactionType(TransactionType.XA)
    public String insertData() {
        return addData();
    }

    public String addData() {
        return addBlog() + "-" + addTitle();
    }

    public String addBlog() {
        for (int i = 0; i < 10; i++) {
            BlogEntity blogEntity = new BlogEntity();
            Integer columnId = new Random().nextInt(999);
            Integer titleId = new Random().nextInt(999);
            blogEntity.setColumnId(columnId);
            blogEntity.setTitle("标题 :" + ((titleId % 2 == 1) ? "表二" : "表一"));
            blogEntity.setTitleId(titleId);
            blogEntity.setAuthor("作者 :" + ((columnId % 2 == 1) ? "数据库二" : "数据库一"));
            blogEntity.setDate(new Date());
            blogDao.save(blogEntity);
        }
        return "blogAddSuccess";
    }

    public String addTitle() {
        for (int i = 0; i < 10; i++) {
            TitleEntity titleEntity = new TitleEntity();
            Integer columnId = new Random().nextInt(999);
            Integer titleId = new Random().nextInt(999);
            titleEntity.setTitle("标题 :" + ((titleId % 2 == 1) ? "表二" : "表一"));
            titleEntity.setSplitId(titleId);
            titleEntity.setTitleName("作者 :" + ((columnId % 2 == 1) ? "数据库二" : "数据库一"));
            titleEntity.setColumnId(columnId);
            titleDao.save(titleEntity);

            if (columnId > 800) {
                throw new RuntimeException("Error : do Trans");
            }
        }
        return "titleAddSuccess";
    }

}
