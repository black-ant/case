package com.gang.study.ibeetl.gangstudyibeetl;

import com.gang.study.ibeetl.gangstudyibeetl.service.ApplicationService;
import com.gang.study.ibeetl.gangstudyibeetl.service.FileResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GangStudyIbeetlApplication {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    FileResourceService fileResourceService;

    public static void main(String[] args) {
        SpringApplication.run(GangStudyIbeetlApplication.class, args);
    }

}
