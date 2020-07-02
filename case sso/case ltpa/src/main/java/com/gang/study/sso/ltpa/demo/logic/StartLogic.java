package com.gang.study.sso.ltpa.demo.logic;

import com.gang.study.sso.ltpa.demo.utils.CryptUtils;
import com.gang.study.sso.ltpa.demo.utils.LtpaTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Classname StartLogic
 * @Description TODO
 * @Date 2020/6/30 17:08
 * @Created by zengzg
 */
@Component
public class StartLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        //        Map<String, String> properties = LtpaTokenUtils.getLtpaProperties();
        //        String code = CryptUtils.DESencode("this is ltpa key store Plaintext", properties.get("KeyPassword"));
        //        logger.info("------> this is token  encode :{}<-------", code);
        //
        //        String decode = CryptUtils.DESdecode(code, properties.get("KeyPassword"));
        //        logger.info("------> this is token decode :{}<-------", decode);


        //        String enCode = CryptUtils.desDecode(properties.get("PrivateKey"), properties.get("KeyPassword"));

        //        String token = LtpaTokenUtils.encode(userService.getUserInfo("admin"), properties);


    }
}
