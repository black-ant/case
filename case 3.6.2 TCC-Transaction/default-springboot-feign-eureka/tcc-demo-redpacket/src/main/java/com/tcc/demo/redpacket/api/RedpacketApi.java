package com.tcc.demo.redpacket.api;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.demo.redpacket.dto.RedPacketTradeOrderDto;
import com.tcc.demo.redpacket.service.RedPacketAccountServiceImpl;
import com.tcc.demo.redpacket.service.RedPacketTradeOrderServiceImpl;

import com.tcc.demo.core.support.TransactionEntity;

@RestController
@RequestMapping("api/redpacket")
public class RedpacketApi {

	@Autowired
	RedPacketAccountServiceImpl redPacketAccountService;
	@Autowired
	RedPacketTradeOrderServiceImpl redPacketTradeOrderService;

	@GetMapping("getRedPacketAccountByUserId")
    public BigDecimal getRedPacketAccountByUserId(long userId) {
		return redPacketAccountService.getRedPacketAccountByUserId(userId);
	}

	@PostMapping("record")
	public String record(@RequestBody TransactionEntity<RedPacketTradeOrderDto> entity) {
		return redPacketTradeOrderService.record(entity.getContext(), entity.getBody());
	}
}
