package com.gang.study.elasticsearch.demo.repository;

import com.gang.study.elasticsearch.demo.entity.AOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname AOrderRepository
 * @Description TODO
 * @Date 2022/3/3
 * @Created by zengzg
 */
@Repository
public interface AOrderRepository extends ElasticsearchRepository<AOrder, String> {
    List<AOrder> findFirstByUsername(String username);
}
