package com.myjpa.demo.controller;

import com.myjpa.demo.entity.api.IOrgEntity;
import com.myjpa.demo.repository.OrgRepository;
import com.myjpa.demo.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname OrgController
 * @Description TODO
 * @Date 2020/8/21 11:06
 * @Created by zengzg
 */
@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgRepository orgRepository;

    @GetMapping("name/{name}")
    public List<IOrgEntity> findByOrgName(@PathVariable("name") String name) {
        return orgService.findByOrgName(name);
    }

    @GetMapping("like/{name}")
    public List<IOrgEntity> findByOrgNameLike(@PathVariable("name") String name) {
        return orgService.findByOrOrgnameIsLike(name);
    }

    @GetMapping("first/{type}")
    public IOrgEntity findFirstByOrgType(@PathVariable("type") String type) {
        return orgRepository.findFirstByOrgType(type);
    }

}
