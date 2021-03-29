package org.mengyun.tcctransaction.sample.http.redpacket.dao;


import org.mengyun.tcctransaction.sample.http.redpacket.entity.RedPacketAccount;

/**
 * Created by changming.xie on 4/2/16.
 */
public interface RedPacketAccountDao {

    RedPacketAccount findByUserId(long userId);

    int update(RedPacketAccount redPacketAccount);
}
