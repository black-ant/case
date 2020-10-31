package com.gang.tcc.transaction.factory;

import com.gang.tcc.transaction.entity.Order;
import com.gang.tcc.transaction.entity.OrderLine;
import com.gang.tcc.transaction.repository.ProductRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by changming.xie on 4/1/16.
 */
@Component
public class OrderFactory {

    @Autowired
    ProductRepository productRepository;

    public Order buildOrder(long payerUserId, long payeeUserId, List<Pair<Long, Integer>> productQuantities) {

        Order order = new Order(payerUserId, payeeUserId);

//        for (Pair<Long, Integer> pair : productQuantities) {
//            long productId = pair.getLeft();
//            order.setOrderLines(new OrderLine(productId, pair.getRight(), productRepository.findById(productId).getPrice()));
//        }

        return order;
    }
}
