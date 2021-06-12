package com.gang.cloud.resilience4j.demo.exceptions;

/**
 * @Classname BusinessAException
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
public class BusinessAException extends RuntimeException {

    public BusinessAException() {
        super();
    }

    public BusinessAException(String message) {
        super(message);
    }

    public BusinessAException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessAException(Throwable cause) {
        super(cause);
    }

    protected BusinessAException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
