package com.search.elastic.controller;

import com.search.elastic.entiry.TemplateDocumentMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname IndexTemplateController
 * @Description TODO
 * @Date 2021/9/9
 * @Created by zengzg
 */
@RestController
@RequestMapping("/index")
public class IndexTemplateController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @GetMapping("start")
    public void createIndexTest() {
        boolean exists = elasticsearchRestTemplate.indexOps(TemplateDocumentMapping.class).exists();
        // 如果索引已存在,删除索引
        if (exists) {
            // 删除索引
            elasticsearchRestTemplate.indexOps(TemplateDocumentMapping.class).delete();
        }

        // 创建索引
        elasticsearchRestTemplate.indexOps(TemplateDocumentMapping.class).create();

        // 创建映射
        Document mappings = elasticsearchRestTemplate.indexOps(TemplateDocumentMapping.class).createMapping();
        elasticsearchRestTemplate.indexOps(TemplateDocumentMapping.class).putMapping(mappings);


        logger.info("------> 执行成功 <-------");
    }

    @GetMapping("delete")
    public void deleteIndex() {
        boolean deleted = elasticsearchRestTemplate.indexOps(TemplateDocumentMapping.class).delete();
        System.out.println("是否删除成功 : " + deleted);
    }
}
