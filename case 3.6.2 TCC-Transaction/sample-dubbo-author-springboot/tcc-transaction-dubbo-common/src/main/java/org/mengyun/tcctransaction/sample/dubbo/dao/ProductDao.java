package org.mengyun.tcctransaction.sample.dubbo.dao;


import org.mengyun.tcctransaction.sample.dubbo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by twinkle.zhou on 16/11/10.
 */
public interface ProductDao extends JpaRepository<Product, Long> {

    Product findByProductId(long productId);

    List<Product> findByShopId(long shopId);
}
