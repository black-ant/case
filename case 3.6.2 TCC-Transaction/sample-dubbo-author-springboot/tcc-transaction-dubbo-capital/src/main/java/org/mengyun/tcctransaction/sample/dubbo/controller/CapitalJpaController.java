package org.mengyun.tcctransaction.sample.dubbo.controller;

import org.mengyun.tcctransaction.sample.dubbo.dao.CapitalAccountDao;
import org.mengyun.tcctransaction.sample.dubbo.entity.CapitalAccount;
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
@RequestMapping("/capital")
public class CapitalJpaController {

    @Autowired
    private CapitalAccountDao capitalAccountDao;

    @GetMapping("list")
    public List<CapitalAccount> list() {
        return capitalAccountDao.findAll();
    }
}
