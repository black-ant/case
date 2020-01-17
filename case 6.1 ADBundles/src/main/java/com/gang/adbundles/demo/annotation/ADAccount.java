package com.gang.adbundles.demo.annotation;

import java.lang.annotation.*;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/5/25 19:53
 * @Version 1.0
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ADAccount {

    public String adName();
    public String refName() default "";



}
