package com.gang.study.pagehelper.demo.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gang.study.pagehelper.demo.entity.SyncType;
import com.gang.study.pagehelper.demo.mapper.SyncTypeDAO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2020/1/21 21:11
 * @Created by zengzg
 */
@RestController
@RequestMapping("/synctype")
public class StartService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SyncTypeDAO syncTypeDAO;

    /**
     * startPage
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("findall/{page}/{size}")
    public String findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        logger.info("------> this is in StartService <-------");

        PageHelper.startPage(page, size);
        List<SyncType> allOrderPresentList = syncTypeDAO.findAll();

        PageInfo<SyncType> pageInfo = new PageInfo<>(allOrderPresentList);

        logger.info("------> pageInfo : {} <-------", pageInfo);
        return JSONObject.toJSONString(pageInfo);
    }

    /**
     * offsetPage
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("findalloffset/{page}/{size}")
    public String findAllByOffsetPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageHelper.offsetPage(page, size);
        List<SyncType> pageInfo = syncTypeDAO.findAll();

        logger.info("------> pageInfo : {} <-------", pageInfo);
        return JSONObject.toJSONString(pageInfo);
    }

    /**
     * lambda 表达式
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("findalllambda/{page}/{size}")
    public String findalllambda(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {

        // 返回实体
        //        Page<SyncType> pageInfo = PageHelper.startPage(page, size).doSelectPage(() -> syncTypeDAO.findAll());

        // 返回pageInfo , pageinfo 返回了很全面得分页属性
        PageInfo pageInfo = PageHelper.startPage(page, size).doSelectPageInfo(() -> syncTypeDAO.findAll());

        logger.info("------> pageInfo : {} <-------", pageInfo);
        return JSONObject.toJSONString(pageInfo);
    }


}
