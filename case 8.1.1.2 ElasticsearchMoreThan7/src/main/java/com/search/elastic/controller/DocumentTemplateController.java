package com.search.elastic.controller;

import com.alibaba.fastjson.JSONObject;
import com.search.elastic.entiry.TemplateDocumentMapping;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Classname DocumentTemplateController
 * @Description TODO
 * @Date 2021/9/9
 * @Created by zengzg
 */
@RestController
@RequestMapping("document")
public class DocumentTemplateController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @RequestMapping("save")
    public void save() {
        TemplateDocumentMapping entity = new TemplateDocumentMapping();
        entity.setUsername("AntBlack" + new Random().nextInt(9999));
        entity.setContent("在武汉登录 , 登录次数 [1]");
        entity.setLoginTime(new Date());
        entity.setPublishDate(LocalDateTime.now());
        elasticsearchRestTemplate.save(entity);
        System.out.println("---插入成功---");
    }

    @RequestMapping("saveList")
    public void saveList() {
        for (int i = 0; i < 10; i++) {
            TemplateDocumentMapping entity = new TemplateDocumentMapping();
            entity.setUsername("AntBlack" + new Random().nextInt(9999));
            entity.setContent("在武汉登录 , 登录次数 [1]");
            entity.setLoginTime(new Date());
            entity.setPublishDate(LocalDateTime.now());
            elasticsearchRestTemplate.save(entity);
            System.out.println("---插入成功---");
        }
    }

    @RequestMapping("saveByUid")
    public String saveByUid() {
        TemplateDocumentMapping entity = new TemplateDocumentMapping();
        entity.setId(UUID.randomUUID().toString());
        entity.setUsername("AntBlack" + new Random().nextInt(9999));
        entity.setContent("在武汉登录 , 登录次数 [1]");
        entity.setLoginTime(new Date());
        entity.setPublishDate(LocalDateTime.now());
        elasticsearchRestTemplate.save(entity);
        System.out.println("---插入成功---");
        return entity.getId();
    }

    @RequestMapping("deleteDoc/{id}")
    public void deleteDoc(@PathVariable String id) {
        // 返回被删除的数据id
        String result = elasticsearchRestTemplate.delete(id, TemplateDocumentMapping.class);
        logger.info("------> getById Document :[{}] <-------", JSONObject.toJSONString(result));
    }

    @RequestMapping("getById/{id}")
    public void getById(@PathVariable String id) {
        TemplateDocumentMapping entity = elasticsearchRestTemplate.get(id, TemplateDocumentMapping.class);
        logger.info("------> getById Document :[{}] <-------", JSONObject.toJSONString(entity));
    }

    @RequestMapping("query/{username}")
    public void query(@PathVariable String username) {
        // match查询,匹配content_字段
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("content_", username)).build();
        SearchHits<TemplateDocumentMapping> list = elasticsearchRestTemplate.search(query, TemplateDocumentMapping.class);
        logger.info("------> query Document :[{}] <-------", JSONObject.toJSONString(list));
    }

    @RequestMapping("filter/{username}")
    public void filter(@PathVariable String username) {
        // match查询,匹配content_字段
        NativeSearchQuery query = new NativeSearchQueryBuilder().withFilter(QueryBuilders.matchQuery("content_", username)).build();
        SearchHits<TemplateDocumentMapping> list = elasticsearchRestTemplate.search(query, TemplateDocumentMapping.class);
        logger.info("------> Filter Document :[{}] <-------", JSONObject.toJSONString(list));
    }
}
