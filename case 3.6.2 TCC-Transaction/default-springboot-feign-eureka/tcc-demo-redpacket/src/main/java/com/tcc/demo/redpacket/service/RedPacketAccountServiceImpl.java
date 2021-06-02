package com.tcc.demo.redpacket.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.demo.redpacket.repository.RedPacketAccountRepository;

/**
 * Created by twinkle.zhou on 16/11/11.
 */
@Service
public class RedPacketAccountServiceImpl {

    @Autowired
    RedPacketAccountRepository redPacketAccountRepository;

    public BigDecimal getRedPacketAccountByUserId(long userId) {
        return redPacketAccountRepository.findByUserId(userId).getBalanceAmount();
    }
}
