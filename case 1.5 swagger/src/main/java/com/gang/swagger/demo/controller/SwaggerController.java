package com.gang.swagger.demo.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {

    @GetMapping("/findname")
    @ApiOperation(value="获取用户列表", notes="")
    public String findByUserName(@RequestParam("username") String username) {
        return "ant-black";
    }

    @PostMapping("/findOrg")
    @ApiOperation(value="获取组织列表", notes="根据orgs对象创建用户")
    @ApiImplicitParam(name = "user", value = "根据orgs对象创建用户", required = true, dataType = "User")
    public String findOrgs(@RequestParam("orgid") String orgid) {
        return "上海派拉";
    }
}
