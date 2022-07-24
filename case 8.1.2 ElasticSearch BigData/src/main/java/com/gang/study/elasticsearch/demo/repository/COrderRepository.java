package com.gang.study.elasticsearch.demo.repository;

import com.gang.study.elasticsearch.demo.entity.BOrder;
import com.gang.study.elasticsearch.demo.entity.COrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Classname COrderRepository
 * @Description TODO
 * @Date 2022/3/3
 * @Created by zengzg
 */
public interface COrderRepository extends ElasticsearchRepository<COrder, String> {
}
