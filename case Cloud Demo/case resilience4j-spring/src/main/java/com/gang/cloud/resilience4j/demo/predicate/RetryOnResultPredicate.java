package com.gang.cloud.resilience4j.demo.predicate;

import java.util.function.Predicate;

/**
 * @Classname RetryOnResultPredicate
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
public class RetryOnResultPredicate implements Predicate<Throwable> {

    @Override
    public boolean test(Throwable throwable) {
        return throwable == null ? true : false;
    }
}
