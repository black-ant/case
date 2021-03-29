package org.mengyun.tcctransaction.sample.dubbo.api;

import org.mengyun.tcctransaction.api.Compensable;
import org.mengyun.tcctransaction.sample.dubbo.api.dto.CapitalTradeOrderDto;

/**
 * Created by changming.xie on 4/1/16.
 */
public interface CapitalTradeOrderService {

//    @Compensable
    String record(CapitalTradeOrderDto tradeOrderDto);

    String testConnect(CapitalTradeOrderDto tradeOrderDto);

}
