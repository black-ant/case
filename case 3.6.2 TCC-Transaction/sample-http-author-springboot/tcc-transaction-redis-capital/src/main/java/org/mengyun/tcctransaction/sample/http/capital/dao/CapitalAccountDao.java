package org.mengyun.tcctransaction.sample.http.capital.dao;


import org.mengyun.tcctransaction.sample.http.capital.entity.CapitalAccount;

/**
 * Created by changming.xie on 4/2/16.
 */
public interface CapitalAccountDao {

    CapitalAccount findByUserId(long userId);

    int update(CapitalAccount capitalAccount);
}
