package com.gang.study.druid.demo.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gang.study.druid.demo.entity.SyncType;
import com.gang.study.druid.demo.mapper.StartMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2020/1/21 21:11
 * @Created by zengzg
 */
@RestController
@RequestMapping("/start")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private StartMapper startMapper;

    @GetMapping("list")
    public String findAll() {
        logger.info("------> this is in StartService <-------");
        QueryWrapper wrapper = new QueryWrapper();
        List<SyncType> allOrderPresentList = startMapper.selectList(wrapper);
        return JSONObject.toJSONString(allOrderPresentList);
    }

    /**
     * 低版本 bug , 连接池中连接出现重复 , 无法回收 , 计数错误
     *
     * @throws Exception
     */
    @GetMapping("test")
    public void test() throws Exception {
        String jdbcUrl = "jdbc:mysql://localhost:3306/gang?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true";
        String user = "root";
        String password = "123456";

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername(user);
        druidDataSource.setPassword(password);
        druidDataSource.setUrl(jdbcUrl);
        druidDataSource.setValidationQuery("select 1");
        druidDataSource.setMinEvictableIdleTimeMillis(10 * 1000);
        druidDataSource.setKeepAliveBetweenTimeMillis(12 * 1000);
        druidDataSource.setMinIdle(2);
        druidDataSource.setMaxWait(1000);
        druidDataSource.setMaxActive(4);
        druidDataSource.setTimeBetweenEvictionRunsMillis(7 * 1000);
        druidDataSource.setKeepAlive(true);
        DruidPooledConnection connection1 = druidDataSource.getConnection();
        DruidPooledConnection connection2 = druidDataSource.getConnection();
        connection2.close();
        Thread.sleep(9 * 1000);
        connection1.close();
        Thread.sleep(14 * 1000);
        DruidPooledConnection connection3 = druidDataSource.getConnection();
        DruidPooledConnection connection4 = druidDataSource.getConnection();
        System.out.println(connection3.getConnectionHolder() == connection4.getConnectionHolder());
        connection3.close();
        connection4.close();
    }


    @GetMapping("thread")
    public void thread() throws Exception {
        String jdbcUrl = "jdbc:mysql://localhost:3306/gang?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true";
        String user = "root";
        String password = "123456";

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUsername(user);
        druidDataSource.setPassword(password);
        druidDataSource.setUrl(jdbcUrl);
        druidDataSource.setValidationQuery("select 1");
        druidDataSource.setMinEvictableIdleTimeMillis(10 * 1000);
        druidDataSource.setKeepAliveBetweenTimeMillis(15 * 1000);
        druidDataSource.setMinIdle(10);
        druidDataSource.setMaxWait(1000);
        druidDataSource.setMaxActive(30);
        druidDataSource.setTimeBetweenEvictionRunsMillis(7 * 1000);
        druidDataSource.setKeepAlive(true);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(200);

        // S1 : 大批量创建连接
        for (int i = 0; i < 20; i++) {
            executor.submit(() -> {
                DruidPooledConnection connection1 = druidDataSource.getConnection();
                Thread.sleep(1 * 1000);
                logger.info("------> connection1 :{} <-------", connection1);
                connection1.close();
                return null;
            });
        }

        Thread.sleep(1 * 1000);
        logger.info("------> 当前可用连接数 :{} <-------", druidDataSource.getActiveCount());

        Thread.sleep(7 * 1000);
        for (int i = 0; i < 30; i++) {
            Thread.sleep(1 * 100);
            logger.info("------> 超过空闲时间的进行关闭 :{} <-------", JSONObject.toJSONString(druidDataSource));
        }


    }


}
