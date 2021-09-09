package com.search.elastic.repository;

import com.search.elastic.entiry.Books;
import com.search.elastic.entiry.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname UserRepository
 * @Description TODO
 * @Date 2021/7/16
 * @Created by zengzg
 */
@Repository
public interface UserRepository extends ElasticsearchRepository<UserEntity, Integer> {

}
