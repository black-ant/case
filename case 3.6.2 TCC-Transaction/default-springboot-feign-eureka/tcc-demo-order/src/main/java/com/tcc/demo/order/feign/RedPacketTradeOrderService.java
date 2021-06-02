package com.tcc.demo.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tcc.demo.order.dto.RedPacketTradeOrderDto;

import com.tcc.demo.core.support.TransactionEntity;

@FeignClient(name = "tcc-redpacket")
public interface RedPacketTradeOrderService {

    @PostMapping("api/redpacket/record")
    String record(@RequestBody TransactionEntity<RedPacketTradeOrderDto> entity);
}
