package org.mengyun.tcctransaction.sample.http.dao;



import org.mengyun.tcctransaction.sample.http.entity.Product;

import java.util.List;

/**
 * Created by twinkle.zhou on 16/11/10.
 */
public interface ProductDao {

    Product findById(long productId);

    List<Product> findByShopId(long shopId);
}
