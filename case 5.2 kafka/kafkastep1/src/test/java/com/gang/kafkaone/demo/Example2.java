package com.gang.kafkaone.demo;

import com.gang.kafkaone.demo.entity.MsgOne;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Example2 {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @Test
    public void testSimple() throws Exception {
        logger.info("is ok start ============== ");
        template.send("topic_1", new MsgOne(1, "msg one ===============").toString());
        logger.info("is ok end ============== ");
    }

    @Test
    public void test2() {
        this.template.send("topic_1", new MsgOne(1, "msg one").toString());
    }
}
