package com.gang.study.dingtalk.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiDepartmentListResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.gang.study.dingtalk.demo.service.OrgService;
import com.gang.study.dingtalk.demo.service.UserService;
import com.gang.study.dingtalk.demo.to.OrgTO;
import com.gang.study.dingtalk.demo.to.UserTO;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Classname UserController
 * @Description TODO
 * @Date 2020/1/19 16:38
 * @Created by zengzg
 */
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * descUrl : http://localhost:8088/org/get/144504620
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public OapiUserGetResponse getRrg(@PathVariable("id") String id) {
        try {
            return userService.getUser(id);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/search/{id}")
    public List<OapiUserSimplelistResponse.Userlist> search(@PathVariable("id") String id) {
        try {
            return userService.getUserList(id);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = "/create")
    public String create(@RequestBody UserTO orgTO) {
        logger.info("------> this is org :{} <-------", JSONObject.toJSONString(orgTO));
        try {
            return userService.create(orgTO);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = "/update")
    public String update(@RequestBody UserTO orgTO) {
        logger.info("------> this is org :{} <-------", JSONObject.toJSONString(orgTO));
        try {
            return userService.update(orgTO);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping(value = "/delete/{key}")
    public String delete(@PathVariable("key") String key) {
        logger.info("------> this is org :{} <-------", key);
        try {
            return userService.delete(key);
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        }
    }
}
