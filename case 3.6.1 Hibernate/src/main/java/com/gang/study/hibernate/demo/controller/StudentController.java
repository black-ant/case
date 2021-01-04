package com.gang.study.hibernate.demo.controller;

import com.gang.study.hibernate.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

/**
 * @Classname StudentController
 * @Description TODO
 * @Date 2020/5/22 23:05
 * @Created by zengzg
 */
@Component
public class StudentController implements ApplicationRunner {

    @Autowired
    private StudentService studentService;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        HashMap<String, Object> map = new HashMap<String, Object>();
        List<Student> list = studentService.get();
        map.put("student:", list);
        map.put("total", list.size());
    }
}
