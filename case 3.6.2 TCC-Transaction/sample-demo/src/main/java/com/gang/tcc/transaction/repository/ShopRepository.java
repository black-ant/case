package com.gang.tcc.transaction.repository;

import com.gang.tcc.transaction.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by changming.xie on 4/1/16.
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Shop findById(String id);
}
