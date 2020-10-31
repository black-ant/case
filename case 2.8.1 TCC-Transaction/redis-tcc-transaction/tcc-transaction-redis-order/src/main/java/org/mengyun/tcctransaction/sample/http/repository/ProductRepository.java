package org.mengyun.tcctransaction.sample.http.repository;

import org.mengyun.tcctransaction.sample.http.dao.ProductDao;
import org.mengyun.tcctransaction.sample.http.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by twinkle.zhou on 16/11/10.
 */
@Repository
public class ProductRepository {

    @Autowired
    ProductDao productDao;

    public Product findById(long productId){
        return productDao.findById(productId);
    }

    public List<Product> findByShopId(long shopId){
        return productDao.findByShopId(shopId);
    }
}
