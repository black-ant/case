package org.mengyun.tcctransaction.sample.dubbo.controller;

import org.mengyun.tcctransaction.sample.dubbo.dao.RedPacketAccountDao;
import org.mengyun.tcctransaction.sample.dubbo.entity.RedPacketAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname CapitalJpaController
 * @Description TODO
 * @Date 2021/3/23
 * @Created by zengzg
 */
@RestController
@RequestMapping("/red")
public class RedJpaController {

    @Autowired
    private RedPacketAccountDao redPacketAccountDao;

    @GetMapping("list")
    public List<RedPacketAccount> list() {
        return redPacketAccountDao.findAll();
    }
}
