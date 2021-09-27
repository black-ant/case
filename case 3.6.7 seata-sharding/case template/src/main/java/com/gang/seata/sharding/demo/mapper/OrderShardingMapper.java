package com.gang.seata.sharding.demo.mapper;

import com.gang.seata.sharding.demo.pojo.OrderSharding;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderShardingMapper {

    int insertOneOrder(OrderSharding orderOne);

    List<OrderSharding> selectAllOrder();
}
