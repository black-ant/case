package com.gang.seata.sharding.demo.mapper;

import com.gang.seata.sharding.demo.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GoodsMapper {

    Goods selectOneGoods(Long goodsId);

    int updateGoodsStock(@Param("goodsId") Long goodsId, @Param("changeAmount") int changeAmount);
}
