package com.saga.clienta.repository;

import com.saga.clienta.entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname HistoryRepository
 * @Description TODO
 * @Date 2021/11/14
 * @Created by zengzg
 */
@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {
}
