package com.gang.demo.exception;

/**
 * @Classname BaseException
 * @Description TODO
 * @Date 2021/6/11
 * @Created by zengzg
 */
public class BaseException extends RuntimeException {

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
