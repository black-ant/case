package com.chapterJPA.demo.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    @Value("Java开发")
    public String work;

}
