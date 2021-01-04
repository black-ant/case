package com.search.elastic.repository;

import com.search.elastic.entiry.Books;
import com.search.elastic.entiry.Orders;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/13 18:51
 * @Version 1.0
 **/
@Repository
public interface OrdersRepository extends ElasticsearchRepository<Orders,Integer> {

    //bool - must 是逻辑查询 ，要求 must 中 的所有结果均为真才可查询出来
    @Query("{'bool' : {'must' : {'field' : {'title' : '开发'}}}}")
    List<Orders> findByTitle(String title);
}
