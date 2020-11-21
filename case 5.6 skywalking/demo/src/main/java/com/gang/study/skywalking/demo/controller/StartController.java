package com.gang.study.skywalking.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.gang.study.skywalking.demo.entity.Group;
import com.gang.study.skywalking.demo.entity.Org;
import com.gang.study.skywalking.demo.entity.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @Classname StartController
 * @Description TODO
 * @Date 2020/11/20 21:32
 * @Created by zengzg
 */
@RestController
@RequestMapping("test")
public class StartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String key = "123456";

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/get")
    public String get() {
        logger.info("------> StartController get<-------");
        User user = (User) redisTemplate.opsForValue().get(key);
        return JSONObject.toJSONString(user);
    }

    @GetMapping("/set")
    public String set() {
        logger.info("------> StartController set<-------");
        User user = new User();
        user.setExpired(Boolean.TRUE);
        user.setOrg(new Org("1111"));

        HashMap map = new HashMap();
        map.put("1", new Org("1111"));
        map.put("2", new Org("2222"));
        map.put("1", new Org("3333"));
        user.setOrgMap(map);

        Set<Group> set = new HashSet<>();
        set.add(new Group("11", "22"));
        user.setGroupSet(set);

        redisTemplate.opsForValue().set(key, user);
        return "success";
    }
}
