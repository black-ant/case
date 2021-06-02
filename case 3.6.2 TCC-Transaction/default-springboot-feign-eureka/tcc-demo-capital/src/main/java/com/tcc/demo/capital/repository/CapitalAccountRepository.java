package com.tcc.demo.capital.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tcc.demo.capital.dao.CapitalAccountDao;
import com.tcc.demo.capital.model.CapitalAccount;

import com.tcc.demo.core.exception.InsufficientBalanceException;

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
