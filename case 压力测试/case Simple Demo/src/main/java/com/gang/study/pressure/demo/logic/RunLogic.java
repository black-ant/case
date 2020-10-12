package com.gang.study.pressure.demo.logic;

import com.gang.study.pressure.demo.to.UserTO;
import com.gang.study.pressure.demo.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

/**
 * @Classname RunLogic
 * @Description TODO
 * @Date 2020/10/10 9:34
 * @Created by zengzg
 */
@Component
public class RunLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private LinkedList<UserTO> userTOS = new LinkedList<>();

    /**
     * 测试 监控程序是否正常
     *
     * @throws Exception
     */
    public void run() {

        logger.info("------> Run Start <-------");

        for (int i = 0; i < 30000; i++) {
            userTOS.add(new UserTO(RandomUtils.getRandomName("gang")));
        }

        logger.info("------> Run Over <-------");

    }

    /**
     * 清除缓存
     */
    public void remove() {
        userTOS.clear();
    }

    public LinkedList<UserTO> getUserTOS() {
        return userTOS;
    }

    public void setUserTOS(LinkedList<UserTO> userTOS) {
        this.userTOS = userTOS;
    }
}
