package org.mengyun.tcctransaction.sample.dubbo.api;

import java.math.BigDecimal;

/**
 * Created by twinkle.zhou on 16/11/11.
 */
public interface RedPacketAccountService {
    BigDecimal getRedPacketAccountByUserId(long userId);
}
