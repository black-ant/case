package org.mengyun.tcctransaction.sample.dubbo.dao;


import org.mengyun.tcctransaction.sample.dubbo.entity.CapitalAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by changming.xie on 4/2/16.
 */
public interface CapitalAccountDao extends JpaRepository<CapitalAccount, Long> {

    CapitalAccount findByUserId(long userId);

}
