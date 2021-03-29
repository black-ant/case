package org.mengyun.tcctransaction.sample.dubbo.order.service;

import org.apache.dubbo.config.annotation.Reference;
import org.mengyun.tcctransaction.sample.dubbo.api.CapitalAccountService;
import org.mengyun.tcctransaction.sample.dubbo.api.RedPacketAccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by twinkle.zhou on 16/11/11.
 */
@Service("accountService")
public class AccountServiceImpl {

    @Reference(lazy = true, check = false)
    RedPacketAccountService redPacketAccountService;

    @Reference(lazy = true, check = false)
    CapitalAccountService capitalAccountService;


    public BigDecimal getRedPacketAccountByUserId(long userId) {
        return redPacketAccountService.getRedPacketAccountByUserId(userId);
    }

    public BigDecimal getCapitalAccountByUserId(long userId) {
        return capitalAccountService.getCapitalAccountByUserId(userId);
    }
}
