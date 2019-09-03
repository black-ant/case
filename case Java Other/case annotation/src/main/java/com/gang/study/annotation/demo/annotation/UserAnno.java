package com.gang.study.annotation.demo.annotation;


import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserAnno {
    String value() default "";
}
