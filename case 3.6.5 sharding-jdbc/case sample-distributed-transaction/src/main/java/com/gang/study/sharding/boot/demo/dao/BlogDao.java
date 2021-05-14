package com.gang.study.sharding.boot.demo.dao;

import com.gang.study.sharding.boot.demo.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Classname BlogDao
 * @Description TODO
 * @Date 2021/5/5
 * @Created by zengzg
 */
public interface BlogDao extends JpaRepository<BlogEntity, Integer> {
}
