package org.mengyun.tcctransaction.sample.dubbo.service;

import org.mengyun.tcctransaction.sample.dubbo.api.CapitalAccountService;
import org.mengyun.tcctransaction.sample.dubbo.repository.CapitalAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by twinkle.zhou on 16/11/11.
 */
@Service("capitalAccountService")
@org.apache.dubbo.config.annotation.Service
public class CapitalAccountServiceImpl implements CapitalAccountService {


    @Autowired
    CapitalAccountRepository capitalAccountRepository;

    @Override
    public BigDecimal getCapitalAccountByUserId(long userId) {
        return capitalAccountRepository.findByUserId(userId).getBalanceAmount();
    }
}
