package com.tcc.demo.redpacket.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tcc.demo.redpacket.model.RedPacketAccount;

/**
 * Created by changming.xie on 4/2/16.
 */
@Mapper
public interface RedPacketAccountDao {

    RedPacketAccount findByUserId(long userId);

    int update(RedPacketAccount redPacketAccount);
}
