package com.gang.study.elasticsearch.demo.service;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @Classname SearchService
 * @Description TODO
 * @Date 2022/3/8
 * @Created by zengzg
 */
@Service
public class SearchService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void hightQuery() throws Exception {
        logger.info("进入高亮处理逻辑");

        // S1 : 通过 Builder 构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder
                .query(QueryBuilders.matchQuery("outAddress", "武汉12"))    // 设置查询条件
                .from(0)    // 起始条数(当前页-1)*size的值
                .size(10)   // 每页展示条数
                .sort("age", SortOrder.DESC)    // 排序
                .highlighter(new HighlightBuilder().field("username").requireFieldMatch(false).preTags("<span style='color:red;'>").postTags("</span>"));  // 设置高亮

        // S2 : 构建查询请求体
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("order").source(searchSourceBuilder);

        // S3 : 发送查询获取 Response
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // S4 : 显示查询结果
        logger.info("查询完成 :{} <-------", JSONObject.toJSONString(searchResponse));
        Arrays.asList(searchResponse.getHits().getHits()).forEach(item -> {
            logger.info("------> ITEM :{} <-------", JSONObject.toJSONString(item.getSourceAsMap()));
        });
    }

    public void sync() throws Exception {
        // S1 : 通过 Builder 构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder
                .query(QueryBuilders.matchQuery("outAddress", "武汉12"))    // 设置查询条件
                .from(0)    // 起始条数(当前页-1)*size的值
                .size(10);   // 每页展示条数

        // S2 : 构建查询请求体
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("order").source(searchSourceBuilder);

        // S3 : 发送查询获取 Response

        Cancellable searchResponse = restHighLevelClient.searchAsync(searchRequest, RequestOptions.DEFAULT, new ActionListener<SearchResponse>() {
            @Override
            public void onResponse(SearchResponse searchResponse) {
                // S4 : 显示查询结果
                logger.info("查询完成 :{} <-------", JSONObject.toJSONString(searchResponse));
                Arrays.asList(searchResponse.getHits().getHits()).forEach(item -> {
                    logger.info("------> ITEM :{} <-------", JSONObject.toJSONString(item.getSourceAsMap()));
                });
            }

            @Override
            public void onFailure(Exception e) {
                logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            }
        });
        
        logger.info("------> 异步处理完成 <-------");
    }


}
