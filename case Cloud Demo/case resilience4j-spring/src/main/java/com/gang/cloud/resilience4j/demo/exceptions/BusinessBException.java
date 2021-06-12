package com.gang.cloud.resilience4j.demo.exceptions;

/**
 * @Classname BusinessBException
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
public class BusinessBException extends RuntimeException {

    public BusinessBException() {
        super();
    }

    public BusinessBException(String message) {
        super(message);
    }

    public BusinessBException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessBException(Throwable cause) {
        super(cause);
    }

    protected BusinessBException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
