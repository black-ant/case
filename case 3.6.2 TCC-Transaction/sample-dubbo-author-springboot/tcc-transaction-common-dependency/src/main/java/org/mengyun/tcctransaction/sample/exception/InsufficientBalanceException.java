package org.mengyun.tcctransaction.sample.exception;

/**
 * @Classname InsufficientBalanceException
 * @Description TODO
 * @Date 2021/3/23
 * @Created by zengzg
 */
public class InsufficientBalanceException extends RuntimeException {
    private static final long serialVersionUID = 6689953065473521009L;

    public InsufficientBalanceException() {

    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}

