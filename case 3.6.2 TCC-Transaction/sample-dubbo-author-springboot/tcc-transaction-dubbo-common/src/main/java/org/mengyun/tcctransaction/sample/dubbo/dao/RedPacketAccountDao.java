package org.mengyun.tcctransaction.sample.dubbo.dao;


import org.mengyun.tcctransaction.sample.dubbo.entity.RedPacketAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by changming.xie on 4/2/16.
 */
public interface RedPacketAccountDao extends JpaRepository<RedPacketAccount, Long> {

    RedPacketAccount findByUserId(long userId);

}
