package com.gang.mongodb.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.mongodb.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Classname AggController
 * @Description TODO
 * @Date 2023/9/2
 * @Created by zengzg
 */
@Slf4j
@RestController
@RequestMapping("/agg")
public class AggController {

    @Resource
    private MongoTemplate mongoTemplate;

    @GetMapping("get")
    public String agg() {
        // 定义聚合操作：按产品名称分组，并计算总销售额和平均销售金额
        GroupOperation groupOperation = Aggregation.group("type")
                .sum("age").as("totalAge")
                .avg("age").as("avgAge");

        // 构建聚合查询
        TypedAggregation<User> aggregation = Aggregation.newAggregation(User.class, groupOperation);

        // 执行聚合查询
        AggregationResults<JSONObject> result = mongoTemplate.aggregate(aggregation, JSONObject.class);

        // 获取聚合结果
        List<JSONObject> productSalesStatsList = result.getMappedResults();


        log.info("------> {} <-------", productSalesStatsList);
        return "sccuess";
    }
}
