package com.gang.webflux.demo.service;

import com.gang.webflux.demo.entity.Person;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname StartService
 * @Description TODO
 * @Date 2021/8/9
 * @Created by zengzg
 */
@Component
public class StartService implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        transform();

        filter();

        doTest();

        log();

    }

    public void transform() {
        Mono.fromCallable(() -> {
            Map<String, String> map = new HashMap<>();
            map.put("name", "瓜田李下");
            map.put("age", "20");

            return map;
        }).flatMap(map -> {
            Person person = new Person();
            person.setName(map.get("name"));
            person.setAge(Integer.parseInt(map.get("age")));

            return Mono.just(person);
        }).subscribe(System.out::println);

        Mono.fromCallable(() -> {
            Map<String, String> map = new HashMap<>();
            map.put("name", "瓜田李下");
            map.put("age", "20");

            return map;
        }).map(map -> {
            Person person = new Person();
            person.setName(map.get("name"));
            person.setAge(Integer.parseInt(map.get("age")));

            return person;
        }).subscribe(System.out::println);

        Mono.fromCallable(() -> {
            Map<String, String> map = new HashMap<>();
            map.put("name", "瓜田李下");
            map.put("age", "20");

            return map;
        }).transform(mono -> mono.map(map -> {
            Person person = new Person();
            person.setName(map.get("name"));
            person.setAge(Integer.parseInt(map.get("age")));

            return person;
        })).subscribe(System.out::println);

        Mono.fromCallable(() -> {
            Map<String, String> map = new HashMap<>();
            map.put("name", "瓜田李下");
            map.put("age", "20");

            return map;
        }).transform(mono -> mono.flatMap(map -> {
            Person person = new Person();
            person.setName(map.get("name"));
            person.setAge(Integer.parseInt(map.get("age")));

            return Mono.just(person);
        })).subscribe(System.out::println);

    }


    public void filter() {
        Mono.just(2).filter(integer -> integer > 1).subscribe(System.out::println);

        System.out.println("\n***************");
        Mono.just(2).filter(integer -> integer > 2).subscribe(System.out::println);
    }


    public void doTest() {
        Mono.just("hello world")
                .doOnNext(s -> System.out.println("next"))
                .doFinally(s -> System.out.println("finally"))
                .doAfterTerminate(() -> System.out.println("terminate"))
                .subscribe(System.out::println);
    }


    public void log() {
        Mono.just("hello world").subscribe(System.out::println);

        System.out.println("\n*************");
        Mono.just("hello world 2").log().subscribe(System.out::println);
    }
}
