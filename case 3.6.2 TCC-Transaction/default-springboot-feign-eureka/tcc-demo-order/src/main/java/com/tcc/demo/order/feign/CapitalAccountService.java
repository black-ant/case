package com.tcc.demo.order.feign;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="tcc-capital")
public interface CapitalAccountService {

	@GetMapping("api/capital/getCapitalAccountByUserId")
    BigDecimal getCapitalAccountByUserId(@RequestParam("userId") long userId);
}


