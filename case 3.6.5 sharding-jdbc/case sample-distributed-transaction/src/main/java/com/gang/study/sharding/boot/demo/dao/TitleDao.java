package com.gang.study.sharding.boot.demo.dao;

import com.gang.study.sharding.boot.demo.entity.BlogEntity;
import com.gang.study.sharding.boot.demo.entity.TitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Classname BlogDao
 * @Description TODO
 * @Date 2021/5/5
 * @Created by zengzg
 */
public interface TitleDao extends JpaRepository<TitleEntity, Integer> {
}
