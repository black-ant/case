package com.gang.study.multiplysource.controller;

import com.gang.study.multiplysource.entityTwo.OrgEntity;
import com.gang.study.multiplysource.jpa.repositorytwo.OrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
