package com.gang.shardingjdbc.demo.repository;

import com.gang.shardingjdbc.demo.entity.AddressEntity;
import com.gang.shardingjdbc.demo.entity.api.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Classname SelfAddressRepository
 * @Description TODO
 * @Date 2021/4/26
 * @Created by zengzg
 */
public interface SelfAddressRepository extends JpaRepository<AddressEntity, Long> {

    AddressEntity findFirstByOrderByAddressIdDesc();

}
