package com.gang.study.elasticsearch.demo.repository;

import com.gang.study.elasticsearch.demo.entity.BOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname BOrderRepository
 * @Description TODO
 * @Date 2022/3/3
 * @Created by zengzg
 */
@Repository
public interface BOrderRepository extends ElasticsearchRepository<BOrder, String> {

}
