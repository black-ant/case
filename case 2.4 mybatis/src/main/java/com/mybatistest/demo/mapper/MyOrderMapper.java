package com.mybatistest.demo.mapper;

import com.mybatistest.demo.entity.MyOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
@Repository
public interface MyOrderMapper {

    List<MyOrder> orderList(String name);

    List<MyOrder> findAll();

    List<MyOrder> findTwoTable();

    List<MyOrder> findTwoTableMany();

    List<MyOrder> findInclude(@Param("startdate") Date start);

    List<MyOrder> findNoParam(@Param("key") String key,@Param("value") String value);

    int insertone(MyOrder order);

    int insertOneReturnId(MyOrder order);

    int insertMany(List<MyOrder> list);

    int insertSearch(MyOrder order);

    int updateParamDescByid(@Param("id") int id, @Param("orderdesc") String orderdesc);

    int updateNumDescByid(int id, String orderdesc);

    int updateMany(MyOrder order);

    int deleteByPrimaryKey(int id);
}
