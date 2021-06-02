package com.tcc.demo.redpacket.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tcc.demo.redpacket.dao.RedPacketAccountDao;
import com.tcc.demo.redpacket.model.RedPacketAccount;

import com.tcc.demo.core.exception.InsufficientBalanceException;

/**
 * Created by changming.xie on 4/2/16.
 */
@Repository
public class RedPacketAccountRepository {

    @Autowired
    RedPacketAccountDao redPacketAccountDao;

    public RedPacketAccount findByUserId(long userId) {

        return redPacketAccountDao.findByUserId(userId);
    }

    public void save(RedPacketAccount redPacketAccount) {
        int effectCount = redPacketAccountDao.update(redPacketAccount);
        if (effectCount < 1) {
            throw new InsufficientBalanceException();
        }
    }
}
