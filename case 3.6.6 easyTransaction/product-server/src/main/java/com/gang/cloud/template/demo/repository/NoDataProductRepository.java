package com.gang.cloud.template.demo.repository;

import com.gang.cloud.template.demo.entity.NoDataProduct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname NoDataProductRepository
 * @Description TODO
 * @Created by zengzg
 */
@Component
public class NoDataProductRepository {

    public final static Integer SUCCESS = 1;
    public final static Integer FAIL = 0;


    /**
     * 模拟数据库存储 , 同步锁
     */
    private ConcurrentHashMap<String, NoDataProduct> database = new ConcurrentHashMap<>();

    /**
     * 通过 ID 获取用户信息
     *
     * @param id
     * @return
     */
    public NoDataProduct getProductById(String id) {
        return database.get(id);
    }

    /**
     * 添加新账户
     *
     * @param product
     * @return
     */
    public int addNoDataProduct(NoDataProduct product) {
        Integer status = FAIL;
        if (product != null && StringUtils.isNotBlank(product.getProductID())) {
            database.put(product.getProductID(), product);
            status = SUCCESS;
        }
        return status;
    }

    /**
     * 更新账户
     *
     * @param Product
     * @return
     */
    public int updateProduct(NoDataProduct Product) {
        Integer status = FAIL;
        if (Product != null && StringUtils.isNotBlank(Product.getProductID()) && null != database.get(Product.getProductID())) {
            database.put(Product.getProductID(), Product);
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
    public int addProductAssets(String id, BigDecimal newAsserts) {

        NoDataProduct Product = database.get(id);
        Integer status = FAIL;
        return status;
    }

    public ConcurrentHashMap<String, NoDataProduct> getDatabase() {
        return database;
    }

}
