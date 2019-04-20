package com.mybatistest.demo.mapper;

import com.mybatistest.demo.entity.MyOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/4/20 18:38
 * @Version 1.0
 **/
@Mapper
@Repository
public interface MyOrderMapper {

    List<MyOrder> orderList(String name);

    List<MyOrder> findAll();
}
