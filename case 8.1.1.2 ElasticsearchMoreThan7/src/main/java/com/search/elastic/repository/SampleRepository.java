package com.search.elastic.repository;

import com.search.elastic.entiry.SampleEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname SampleRepository
 * @Description TODO
 * @Date 2021/7/15
 * @Created by zengzg
 */
@Repository
public interface SampleRepository extends ElasticsearchRepository<SampleEntity, Integer> {


}
