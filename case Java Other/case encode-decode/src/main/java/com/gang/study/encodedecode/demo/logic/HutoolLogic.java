package com.gang.study.encodedecode.demo.logic;

import com.gang.study.encodedecode.demo.hutools.HutoolsUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname HutoolLogic
 * @Description TODO
 * @Date 2020/6/30 22:00
 * @Created by zengzg
 */
@Component
public class HutoolLogic implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {

        HutoolsUtils utils = new HutoolsUtils();

        String info = "antBlackZzg";

        //        utils.md5(info);
        //        utils.generateKey();
        //        utils.generateKeyHavaLength();
        //        utils.generateKeyHavaKeySpec();
        //        utils.generatePublicKey();
        utils.aesBuild();
    }

}
