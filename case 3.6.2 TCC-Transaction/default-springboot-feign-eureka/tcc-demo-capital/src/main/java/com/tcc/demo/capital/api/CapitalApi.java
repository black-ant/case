package com.tcc.demo.capital.api;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.demo.capital.dto.CapitalTradeOrderDto;
import com.tcc.demo.capital.service.CapitalAccountServiceImpl;
import com.tcc.demo.capital.service.CapitalTradeOrderServiceImpl;

import com.tcc.demo.core.support.TransactionEntity;

@RestController
@RequestMapping("api/capital")
public class CapitalApi {

	@Autowired
	CapitalAccountServiceImpl capitalAccountService;
	@Autowired
	CapitalTradeOrderServiceImpl capitalTradeOrderService;

	@GetMapping("getCapitalAccountByUserId")
	public BigDecimal getCapitalAccountByUserId(long userId) {
		return capitalAccountService.getCapitalAccountByUserId(userId);
	}

	@PostMapping("record")
	public String record(@RequestBody TransactionEntity<CapitalTradeOrderDto> entity) {
		return capitalTradeOrderService.record(entity.getContext(), entity.getBody());
	}
}
