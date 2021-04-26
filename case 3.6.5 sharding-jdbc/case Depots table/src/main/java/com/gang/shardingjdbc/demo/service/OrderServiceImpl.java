/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gang.shardingjdbc.demo.service;

import com.gang.shardingjdbc.demo.entity.AddressEntity;
import com.gang.shardingjdbc.demo.entity.OrderEntity;
import com.gang.shardingjdbc.demo.entity.OrderItemEntity;
import com.gang.shardingjdbc.demo.entity.api.Address;
import com.gang.shardingjdbc.demo.entity.api.Order;
import com.gang.shardingjdbc.demo.repository.SelfAddressRepository;
import com.gang.shardingjdbc.demo.repository.SelfOrderRepository;
import com.gang.shardingjdbc.demo.repository.api.OrderItemRepository;
import com.gang.shardingjdbc.demo.service.api.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Primary
public class OrderServiceImpl implements ExampleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OrderItemRepository orderItemRepository;

    @Autowired
    private SelfAddressRepository selfAddressRepository;

    @Autowired
    private SelfOrderRepository selfOrderRepository;

    @Override
    public void initEnvironment() throws SQLException {
        Address address = selfAddressRepository.findFirstByOrderByAddressIdDesc();
        Long keyFirst = address == null ? 0 : address.getAddressId();
        for (int i = keyFirst.intValue(); i <= (keyFirst + 10); i++) {
            AddressEntity entity = new AddressEntity();
            entity.setAddressId((long) i);
            entity.setAddressName("address_" + i);
            selfAddressRepository.save(entity);
        }
    }

    @Override
    public void cleanEnvironment() {
    }

    @Override
    @Transactional
    public void processSuccess() throws SQLException {
        System.out.println("-------------- Process Success Begin ---------------");

        Order orderOld = selfOrderRepository.findFirstByOrderByOrderIdDesc();
        Long keyFirst = orderOld == null ? 0 : orderOld.getAddressId();
        logger.info("------> [获取 Order 表主键 :{}] <-------", keyFirst);

        // 测试插入单行
        insertOne();

//        List<Long> orderIds = insertData(keyFirst.intValue());
//
//        printData();
//        deleteData(orderIds);
//        printData();

        System.out.println("-------------- Process Success Finish --------------");
    }

    /**
     * 测试插入一条记录
     */
    public void insertOne() {
        OrderEntity order = new OrderEntity();
        Long key = Long.valueOf(new Random().nextInt(99999));
        order.setOrderId(key);
        order.setUserId(key.intValue());
        order.setAddressId(key);
        order.setStatus("INSERT_TEST_JPA");
        selfOrderRepository.save(order);
        logger.info("------> [测试当行插入完成 :{}] <-------", key);
    }

    @Override
    @Transactional
    public void processFailure() throws SQLException {
        System.out.println("-------------- Process Failure Begin ---------------");

        Order orderOld = selfOrderRepository.findFirstByOrderByOrderIdDesc();
        Long keyFirst = orderOld == null ? 0 : orderOld.getAddressId();
        logger.info("------> [获取 Order 表主键 :{}] <-------", keyFirst);

//        insertData(firstKey);
        System.out.println("-------------- Process Failure Finish --------------");
        throw new RuntimeException("Exception occur for transaction test.");
    }

    private List<Long> insertData(int firstKey) throws SQLException {
        System.out.println("---------------------------- Insert Data ----------------------------");
        List<Long> result = new ArrayList<>(10);

        for (int i = firstKey; i <= (firstKey + 1); i++) {
            OrderEntity order = new OrderEntity();
            order.setOrderId(i);
            order.setUserId(i);
            order.setAddressId(i);
            order.setStatus("INSERT_TEST_JPA");
            selfOrderRepository.save(order);
            OrderItemEntity item = new OrderItemEntity();
            item.setOrderItemId(i);
            item.setOrderId(order.getOrderId());
            item.setUserId(i);
            item.setStatus("INSERT_TEST_JPA");
//            orderItemRepository.insert(item);
            result.add(order.getOrderId());
        }
        return result;
    }

    private void deleteData(final List<Long> orderIds) throws SQLException {
        System.out.println("---------------------------- Delete Data ----------------------------");
        for (Long each : orderIds) {
            selfOrderRepository.deleteById(each);
//            orderItemRepository.delete(each);
        }
    }

    @Override
    public void printData() throws SQLException {
        System.out.println("---------------------------- Print Order Data -----------------------");
//        for (Object each : selfOrderRepository.findAll()) {
//            System.out.println(each);
//        }
        System.out.println("---------------------------- Print OrderItem Data -------------------");
//        for (Object each : orderItemRepository.selectAll()) {
//            System.out.println(each);
//        }
    }
}
