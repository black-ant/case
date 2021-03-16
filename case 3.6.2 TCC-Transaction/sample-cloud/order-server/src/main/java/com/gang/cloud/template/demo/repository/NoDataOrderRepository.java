package com.gang.cloud.template.demo.repository;

import com.gang.cloud.template.demo.entity.NoDataOrder;
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
public class NoDataOrderRepository {

    public final static Integer SUCCESS = 1;
    public final static Integer FAIL = 0;


    /**
     * 模拟数据库存储 , 同步锁
     */
    private ConcurrentHashMap<String, NoDataOrder> database = new ConcurrentHashMap<>();

    /**
     * 通过 ID 获取用户信息
     *
     * @param id
     * @return
     */
    public NoDataOrder getOrderById(String id) {
        return database.get(id);
    }

    /**
     * 添加新账户
     *
     * @param order
     * @return
     */
    public int addOrder(NoDataOrder order) {
        Integer status = FAIL;
        if (order != null && StringUtils.isNotBlank(order.getOrderId())) {
            database.put(order.getOrderId(), order);
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

        NoDataOrder account = database.get(id);
        Integer status = FAIL;


        return status;
    }

    public ConcurrentHashMap<String, NoDataOrder> getDatabase() {
        return database;
    }

}
