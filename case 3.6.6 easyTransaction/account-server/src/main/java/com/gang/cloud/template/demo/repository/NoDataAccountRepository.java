package com.gang.cloud.template.demo.repository;

import com.gang.cloud.template.demo.entity.NoDataAccount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname NoDataAccountRepository
 * @Description TODO
 * @Created by zengzg
 */
@Component
public class NoDataAccountRepository {

    public final static Integer SUCCESS = 1;
    public final static Integer FAIL = 0;


    /**
     * 模拟数据库存储 , 同步锁
     */
    private ConcurrentHashMap<String, NoDataAccount> database = new ConcurrentHashMap<>();

    /**
     * 通过 ID 获取用户信息
     *
     * @param id
     * @return
     */
    public NoDataAccount getAccountById(String id) {
        return database.get(id);
    }

    /**
     * 添加新账户
     *
     * @param account
     * @return
     */
    public int addAccount(NoDataAccount account) {
        Integer status = FAIL;
        if (account != null && StringUtils.isNotBlank(account.getAccountId())) {
            database.put(account.getAccountId(), account);
            status = SUCCESS;
        }
        return status;
    }

    /**
     * 更新账户
     *
     * @param account
     * @return
     */
    public int updateAccount(NoDataAccount account) {
        Integer status = FAIL;
        if (account != null && StringUtils.isNotBlank(account.getAccountId()) && null != database.get(account.getAccountId())) {
            database.put(account.getAccountId(), account);
            status = SUCCESS;
        }
        return status;
    }

    /**
     * 更新新账户积分
     *
     * @param id         用户账号
     * @param newAsserts 新账户金额
     * @return
     */
    public int addAccountAssets(String id, BigDecimal newAsserts) {

        NoDataAccount account = database.get(id);
        Integer status = FAIL;

        if (account != null) {
            account.setAccountAssets(newAsserts);
            status = SUCCESS;
        }

        return status;
    }

    public ConcurrentHashMap<String, NoDataAccount> getDatabase() {
        return database;
    }
}
