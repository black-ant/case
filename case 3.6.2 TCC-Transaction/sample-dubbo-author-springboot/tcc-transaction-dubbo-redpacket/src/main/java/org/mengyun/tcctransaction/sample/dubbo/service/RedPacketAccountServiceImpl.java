package org.mengyun.tcctransaction.sample.dubbo.service;

import org.mengyun.tcctransaction.sample.dubbo.api.RedPacketAccountService;
import org.mengyun.tcctransaction.sample.dubbo.repository.RedPacketAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by twinkle.zhou on 16/11/11.
 */
@Service("redPacketAccountService")
@org.apache.dubbo.config.annotation.Service
public class RedPacketAccountServiceImpl implements RedPacketAccountService {

    @Autowired
    RedPacketAccountRepository redPacketAccountRepository;

    @Override
    public BigDecimal getRedPacketAccountByUserId(long userId) {
        return redPacketAccountRepository.findByUserId(userId).getBalanceAmount();
    }
}
