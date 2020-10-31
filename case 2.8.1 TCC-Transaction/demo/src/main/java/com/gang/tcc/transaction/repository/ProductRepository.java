package com.gang.tcc.transaction.repository;

import com.gang.tcc.transaction.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by twinkle.zhou on 16/11/10.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    Product findById(long productId);

    List<Product> findByShopId(long shopId);
}
