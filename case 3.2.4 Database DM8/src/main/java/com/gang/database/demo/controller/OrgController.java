package com.gang.database.demo.controller;

import com.gang.study.wycat.mycat.entity.OrgEntity;
import com.gang.study.wycat.mycat.repository.OrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @Classname OrgController
 * @Description TODO
 * @Date 2021/1/16 16:58
 * @Created by zengzg
 */
@RestController
@RequestMapping("org")
public class OrgController {

    @Autowired
    private OrgRepository orgRepository;

    @GetMapping("/get")
    public OrgEntity getOrg(@RequestParam("name") String orgName) {
        return orgRepository.findFirstByOrgname(orgName);
    }

    @GetMapping("/getAll")
    public List<OrgEntity> getAll() {
        return orgRepository.findAll();
    }

    @GetMapping("/create")
    public String create() {
        for (int i = 0; i < 10; i++) {
            int num = new Random().nextInt(99999);
            OrgEntity entity = new OrgEntity();
            entity.setOrgname("name_" + num);
            orgRepository.save(entity);
        }

        return "success";
    }


}
