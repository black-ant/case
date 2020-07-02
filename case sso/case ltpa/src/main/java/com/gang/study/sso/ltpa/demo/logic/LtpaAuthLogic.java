package com.gang.study.sso.ltpa.demo.logic;

import com.gang.study.sso.ltpa.demo.entity.UserInfo;
import com.gang.study.sso.ltpa.demo.to.UserMetadata;
import com.gang.study.sso.ltpa.demo.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Classname LtpaCheckService
 * @Description TODO
 * @Date 2020/6/30 16:01
 * @Created by zengzg
 */
@Component
public class LtpaAuthLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private LtpaAlogrithmManager manager;

    /**
     * 模拟登录请求
     */
    public String doLogin() {
        Map<String, String> propertie = manager.getLtpaProperties();

        UserInfo userInfo = userService.getUserInfo("admin");

        String token = manager.encode(userInfo, propertie);

        logger.info("------> this is token :{} <-------", token);

        return token;

    }

    /**
     * 模拟认证请求
     */
    public UserMetadata doCheck(HttpServletRequest request) {
        String token = CookieUtils.getCookieValue(request, "ssoLoginToken");
        Map<String, String> propertie = manager.getLtpaProperties();
        return manager.decode(token, propertie);
    }


}
