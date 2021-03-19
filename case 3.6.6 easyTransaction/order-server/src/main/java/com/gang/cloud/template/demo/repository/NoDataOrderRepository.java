package com.gang.cloud.template.demo.repository;

import com.gang.cloud.template.to.CommonOrderTO;
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
    private ConcurrentHashMap<Integer, CommonOrderTO> database = new ConcurrentHashMap<>();

    /**
     * 通过 ID 获取用户信息
     *
     * @param id
     * @return
     */
    public CommonOrderTO getOrderById(Integer id) {
        return database.get(id);
    }

    /**
     * 添加新账户
     *
     * @param order
     * @return
     */
    public int addOrder(CommonOrderTO order) {
        Integer status = FAIL;
        if (order != null && order.getOrderId() != null) {
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

        CommonOrderTO account = database.get(id);
        Integer status = FAIL;


        return status;
    }

    public ConcurrentHashMap<Integer, CommonOrderTO> getDatabase() {
        return database;
    }

}
