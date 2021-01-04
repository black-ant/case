package com.gang.study.pagehelper.demo.mapper;

import com.gang.study.pagehelper.demo.entity.SyncType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Component
public interface SyncTypeDAO {

    int deleteByPrimaryKey(String id);

    int insert(SyncType record);

    List<SyncType> findAll();

    int insertSelective(SyncType record);

    SyncType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SyncType record);

    int updateByPrimaryKey(SyncType record);
}