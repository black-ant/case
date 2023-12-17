package com.gang.mongodb.demo.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Classname Person
 * @Description TODO
 * @Date 2021/9/21
 * @Created by zengzg
 */
@Data
@Document(collection = "person")
public class Person  {

    private Long id;

    private String username;

    private Integer age;

}
