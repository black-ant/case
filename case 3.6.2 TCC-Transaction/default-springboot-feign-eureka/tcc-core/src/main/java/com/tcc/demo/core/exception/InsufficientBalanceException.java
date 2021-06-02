package com.tcc.demo.core.exception;

/**
 * Created by changming.xie on 11/21/17.
 */
public class InsufficientBalanceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InsufficientBalanceException() {

    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
