package com.gang.junit.h2.demoh2;

import com.gang.junit.h2.demoh2.entity.TestEntitiy;
import com.gang.junit.h2.demoh2.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Classname DemoH2ApplicationTests2
 * @Description TODO
 * @Date 2020/10/12 22:58
 * @Created by zengzg
 */
@RunWith(SpringRunner.class)
@DataJpaTest
class DemoH2ApplicationTests2 {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TestRepository repository;

    @Test
    public void test() {
        TestEntitiy u = repository.getOne("1");
        logger.info("------> this is TestEntity :{} <-------", u);
    }


}
