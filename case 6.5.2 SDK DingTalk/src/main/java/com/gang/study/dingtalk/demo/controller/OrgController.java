package com.gang.study.dingtalk.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.gang.study.dingtalk.demo.service.OrgService;
import com.gang.study.dingtalk.demo.to.OrgTO;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname OrgController
 * @Description TODO
 * @Date 2020/1/19 14:47
 * @Created by zengzg
 */
@RequestMapping(value = "/org", produces = "application/json;charset=UTF-8", consumes = "application/json;" +
        "charset=UTF-8")
@RestController
public class OrgController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrgService orgService;

    /**
     * descUrl : http://localhost:8088/org/get/144504620
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public OapiDepartmentGetResponse getRrg(@PathVariable("id") String id) {
        try {
            return orgService.getOrg(id);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/search/{id}")
    public List<OapiDepartmentListResponse.Department> search(@PathVariable("id") String id) {
        try {
            return orgService.getOrgList(id);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = "/create")
    public String create(@RequestBody OrgTO orgTO) {
        logger.info("------> this is org :{} <-------", JSONObject.toJSONString(orgTO));
        try {
            return orgService.create(orgTO);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = "/update")
    public String update(@RequestBody OrgTO orgTO) {
        logger.info("------> this is org :{} <-------", JSONObject.toJSONString(orgTO));
        try {
            return orgService.update(orgTO);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = "/delete/{key}")
    public String delete(@PathVariable("key") String key) {
        logger.info("------> this is org :{} <-------", key);
        try {
            return orgService.delete(key);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

}
