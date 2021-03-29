package org.mengyun.tcctransaction.sample.dubbo.repository;

import org.mengyun.tcctransaction.sample.dubbo.dao.CapitalAccountDao;
import org.mengyun.tcctransaction.sample.dubbo.entity.CapitalAccount;
import org.mengyun.tcctransaction.sample.exception.InsufficientBalanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by changming.xie on 4/2/16.
 */
@Repository
public class CapitalAccountRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CapitalAccountDao capitalAccountDao;

    public CapitalAccount findByUserId(long userId) {

        return capitalAccountDao.findByUserId(userId);
    }

    public void save(CapitalAccount capitalAccount) {
        try {
            CapitalAccount effectCount = capitalAccountDao.save(capitalAccount);
        } catch (Exception e) {
            logger.error("E----> error :{} -- content :{}", e.getClass(), e.getMessage());
            throw new InsufficientBalanceException();
        }
    }
}
