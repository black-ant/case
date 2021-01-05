package com.gang.study.module.gangstudymodule.dao;

import com.gang.study.module.gangstudymodule.entity.MyOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/20 18:38
 * @Version 1.0
 **/
@Mapper
@Component
public interface MyOrderMapper {

    List<MyOrder> findAll();

}
