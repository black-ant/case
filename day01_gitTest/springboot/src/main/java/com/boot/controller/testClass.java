package com.boot.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testClass extends AbstractController{

    @RequestMapping("/main/{value1}")
    public void testMethod(@PathVariable("value1") String value){

    }
}
