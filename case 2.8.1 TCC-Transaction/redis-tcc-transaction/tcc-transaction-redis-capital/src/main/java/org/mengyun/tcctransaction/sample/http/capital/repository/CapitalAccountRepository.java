package org.mengyun.tcctransaction.sample.http.capital.repository;

import org.mengyun.tcctransaction.sample.exception.InsufficientBalanceException;
import org.mengyun.tcctransaction.sample.http.capital.dao.CapitalAccountDao;
import org.mengyun.tcctransaction.sample.http.capital.entity.CapitalAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by changming.xie on 4/2/16.
 */
@Repository
public class CapitalAccountRepository {

    @Autowired
    CapitalAccountDao capitalAccountDao;

    public CapitalAccount findByUserId(long userId) {

        return capitalAccountDao.findByUserId(userId);
    }

    public void save(CapitalAccount capitalAccount) {
        int effectCount = capitalAccountDao.update(capitalAccount);
        if (effectCount < 1) {
            throw new InsufficientBalanceException();
        }
    }
}
