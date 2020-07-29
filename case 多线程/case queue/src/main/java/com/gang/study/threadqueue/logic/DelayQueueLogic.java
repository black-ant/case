package com.gang.study.threadqueue.logic;

import com.gang.study.threadqueue.to.UserDelayQueueTO;
import com.gang.study.threadqueue.to.UserTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.DelayQueue;

/**
 * @Classname DelayQueueLogic
 * @Description TODO
 * @Date 2020/7/23 17:51
 * @Created by zengzg
 */
@Component
public class DelayQueueLogic extends BaseQueueLogic {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> this is in start  DelayQueueLogic<-------");

        DelayQueue queue = new DelayQueue();
        queue.offer(createUserDelayQueueTO());
        queue.offer(createUserDelayQueueTO());
        queue.offer(createUserDelayQueueTO());
        queue.offer(createUserDelayQueueTO());

        System.out.println("begin time:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        for (int i = 0; i < 3; i++) {
            UserDelayQueueTO take = (UserDelayQueueTO) queue.take();
            System.out.format("name:{%s}, time:{%s}\n", take.getUsername(), LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
    }

    public UserDelayQueueTO createUserDelayQueueTO() {
        UserDelayQueueTO userTO = new UserDelayQueueTO();
        Integer num = new Random().nextInt(9999);
        userTO.setUserid(num);
        userTO.setUsername("GANG" + num);
        return userTO;
    }
}
