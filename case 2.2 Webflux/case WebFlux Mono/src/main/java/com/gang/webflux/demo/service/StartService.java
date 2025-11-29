package com.gang.webflux.demo.service;

import com.gang.webflux.demo.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Mono 操作符示例服务
 * <p>
 * 演示 Reactor Mono 的各种操作符用法：
 * <ul>
 *     <li>transform - 数据转换</li>
 *     <li>filter - 数据过滤</li>
 *     <li>doOnXxx - 生命周期回调</li>
 *     <li>log - 日志记录</li>
 * </ul>
 * </p>
 *
 * @author zengzg
 * @since 2021/8/9
 */
@Component
public class StartService implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartService.class);

    @Override
    public void run(ApplicationArguments args) {
        logger.info("=== Starting Mono Demo ===");
        
        transformDemo();
        filterDemo();
        doCallbacksDemo();
        logDemo();
        
        logger.info("=== Mono Demo Completed ===");
    }

    /**
     * 数据转换操作符示例
     * <p>
     * 演示 map、flatMap、transform 的区别：
     * <ul>
     *     <li>map - 同步转换，返回普通值</li>
     *     <li>flatMap - 异步转换，返回 Mono/Flux</li>
     *     <li>transform - 对整个流进行变换</li>
     * </ul>
     * </p>
     */
    public void transformDemo() {
        logger.info("--- Transform Demo ---");
        
        // 1. 使用 flatMap（返回 Mono）
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
        }).subscribe(p -> logger.info("flatMap result: {}", p));

        // 2. 使用 map（返回普通值）
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
        }).subscribe(p -> logger.info("map result: {}", p));

        // 3. 使用 transform（变换整个流）
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
        })).subscribe(p -> logger.info("transform result: {}", p));
    }

    /**
     * 过滤操作符示例
     * <p>
     * filter 操作符：只有满足条件的元素才会被传递给订阅者。
     * 如果条件不满足，Mono 将为空（不发出任何元素）。
     * </p>
     */
    public void filterDemo() {
        logger.info("--- Filter Demo ---");
        
        // 条件满足 - 输出 2
        Mono.just(2)
                .filter(integer -> integer > 1)
                .subscribe(v -> logger.info("filter (> 1): {}", v));

        // 条件不满足 - 无输出
        Mono.just(2)
                .filter(integer -> integer > 2)
                .defaultIfEmpty(-1)
                .subscribe(v -> logger.info("filter (> 2): {}", v));
    }

    /**
     * 生命周期回调示例
     * <p>
     * doOnXxx 系列操作符用于在特定事件发生时执行副作用：
     * <ul>
     *     <li>doOnNext - 每个元素发出时</li>
     *     <li>doOnError - 发生错误时</li>
     *     <li>doOnComplete - 正常完成时</li>
     *     <li>doFinally - 无论如何都会执行</li>
     *     <li>doAfterTerminate - 终止后执行</li>
     * </ul>
     * </p>
     */
    public void doCallbacksDemo() {
        logger.info("--- Callbacks Demo ---");
        
        Mono.just("hello world")
                .doOnNext(s -> logger.info("doOnNext: {}", s))
                .doOnSuccess(s -> logger.info("doOnSuccess: {}", s))
                .doFinally(signal -> logger.info("doFinally: signal={}", signal))
                .doAfterTerminate(() -> logger.info("doAfterTerminate"))
                .subscribe(s -> logger.info("subscribe: {}", s));
    }

    /**
     * 日志操作符示例
     * <p>
     * log() 操作符会记录所有 Reactive Streams 信号：
     * onSubscribe, onNext, onError, onComplete, request, cancel
     * </p>
     */
    public void logDemo() {
        logger.info("--- Log Demo ---");
        
        // 无日志
        Mono.just("hello world")
                .subscribe(s -> logger.info("without log: {}", s));

        // 带日志 - 会输出详细的响应式流信号
        Mono.just("hello world 2")
                .log()
                .subscribe(s -> logger.info("with log: {}", s));
    }
}
