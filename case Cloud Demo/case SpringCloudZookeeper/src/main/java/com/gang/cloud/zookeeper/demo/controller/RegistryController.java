package com.gang.cloud.zookeeper.demo.controller;

import com.gang.cloud.zookeeper.demo.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname RegistryController
 * @Description TODO
 * @Date 2021/8/2
 * @Created by zengzg
 */
@RestController
@RequestMapping("registry")
public class RegistryController {

    @Autowired
    private RegistryService registryService;

    @GetMapping("add")
    public String addOne(@RequestParam(name = "serviceName", defaultValue = "test") String serviceName,
                         @RequestParam(name = "address", defaultValue = "127.0.0.1") String address,
                         @RequestParam(name = "port", defaultValue = "8086") Integer port) {

        registryService.registerThings(serviceName, address, port);
        return "success ack";
    }

    @GetMapping("search/{name}")
    public String searchRegistry(@PathVariable("name") String name) {
        registryService.searchRegistry(name);
        return "success ack";
    }

    @GetMapping("delete/{name}")
    public String deleteRegistry(@PathVariable("name") String name) {
        registryService.deleteRegistry(name);
        return "success ack";
    }


}
