package com.gang.cloud.template.demo.repository;

import com.gang.cloud.template.demo.entity.OrderEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname NoDataAccountRepository
 * @Description TODO
 * @Created by zengzg
 */
@Component
public interface OrderRepository extends JpaRepository<OrderEntity, String> {


}
