package com.gang.study.oauth.dingtalk.demo;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Classname UserInfoController
 * @Description TODO
 * @Date 2020/5/1 21:26
 * @Created by zengzg
 */
@RequestMapping("/user")
@RestController
public class UserInfoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestController testController;

    @GetMapping("/test/{type}")
    public String test(@PathVariable("type") String type, HttpServletRequest servletRequest) {
        logger.info("------> servletRequest :{} <-------", servletRequest.getRequestURI());
        logger.info("------> type is :{} <-------", type);
        logger.info("------> servletRequest :{} <-------", JSONObject.toJSONString(servletRequest.getParameterMap()));

        Map<String, String[]> requestParam = servletRequest.getParameterMap();
        try {
            testController.run(requestParam.get("code")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
