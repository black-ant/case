package com.gang.junit.h2.demoh2;

import com.gang.junit.h2.demoh2.entity.TestEntitiy;
import com.gang.junit.h2.demoh2.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class DemoH2ApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestRepository repository;

    @Test
    public void test() {
        TestEntitiy u = repository.getOne("1");
        logger.info("------> this is TestEntity :{} <-------", u);
    }


}
