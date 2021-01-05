package com.gang.study.mapstruct.demo;

import com.gang.study.mapstruct.demo.to.PersonDO;
import com.gang.study.mapstruct.demo.to.PersonDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2020/8/22 15:37
 * @Created by zengzg
 */
@Service
public class StartService implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        PersonDO personDO = new PersonDO();
        personDO.setName("Hollis");
        personDO.setAge(26);
        personDO.setBirthday(new Date());
        personDO.setId(1);
        PersonDTO personDTO = PersonConverter.INSTANCE.do2dto(personDO);

        logger.info("------> this is :{} <-------", personDTO.toString());
    }
}
