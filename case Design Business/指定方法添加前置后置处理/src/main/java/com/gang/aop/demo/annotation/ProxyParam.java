package com.gang.aop.demo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/**
 * @Classname ProxyParam
 * @Description TODO
 * @Date 2020/10/28 21:34
 * @Created by zengzg
 */
@Target(value = {ElementType.PARAMETER})
@Documented
@Inherited
public @interface ProxyParam {

    String paramName() default "";
}
