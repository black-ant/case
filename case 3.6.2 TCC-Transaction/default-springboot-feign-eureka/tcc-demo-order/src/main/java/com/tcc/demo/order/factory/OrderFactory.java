package com.tcc.demo.order.factory;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tcc.demo.order.model.Order;
import com.tcc.demo.order.model.OrderLine;
import com.tcc.demo.order.repository.ProductRepository;

/**
 * Created by changming.xie on 4/1/16.
 */
@Component
public class OrderFactory {

    @Autowired
    ProductRepository productRepository;

    public Order buildOrder(long payerUserId, long payeeUserId, List<Pair<Long, Integer>> productQuantities) {

        Order order = new Order(payerUserId, payeeUserId);

        for (Pair<Long, Integer> pair : productQuantities) {
            long productId = pair.getLeft();
            order.addOrderLine(new OrderLine(productId, pair.getRight(), productRepository.findById(productId).getPrice()));
        }

        return order;
    }
}
