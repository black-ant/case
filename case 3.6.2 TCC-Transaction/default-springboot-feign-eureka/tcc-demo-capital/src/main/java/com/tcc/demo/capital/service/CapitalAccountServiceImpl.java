package com.tcc.demo.capital.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.demo.capital.repository.CapitalAccountRepository;

/**
 * Created by twinkle.zhou on 16/11/11.
 */
@Service
public class CapitalAccountServiceImpl {


    @Autowired
    CapitalAccountRepository capitalAccountRepository;

    public BigDecimal getCapitalAccountByUserId(long userId) {
        return capitalAccountRepository.findByUserId(userId).getBalanceAmount();
    }
}
