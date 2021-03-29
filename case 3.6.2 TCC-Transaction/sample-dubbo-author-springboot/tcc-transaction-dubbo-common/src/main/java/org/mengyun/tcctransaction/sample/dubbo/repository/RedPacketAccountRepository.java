package org.mengyun.tcctransaction.sample.dubbo.repository;


import org.mengyun.tcctransaction.sample.dubbo.dao.RedPacketAccountDao;
import org.mengyun.tcctransaction.sample.dubbo.entity.RedPacketAccount;
import org.mengyun.tcctransaction.sample.exception.InsufficientBalanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by changming.xie on 4/2/16.
 */
@Repository
public class RedPacketAccountRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedPacketAccountDao redPacketAccountDao;

    public RedPacketAccount findByUserId(long userId) {

        return redPacketAccountDao.findByUserId(userId);
    }

    public void save(RedPacketAccount redPacketAccount) {
        try {
            redPacketAccountDao.save(redPacketAccount);
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            throw new InsufficientBalanceException();
        }
    }
}
