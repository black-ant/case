package com.tcc.demo.order.feign;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="tcc-redpacket")
public interface RedPacketAccountService {

	@GetMapping("api/redpacket/getRedPacketAccountByUserId")
    BigDecimal getRedPacketAccountByUserId(@RequestParam("userId") long userId);
}
