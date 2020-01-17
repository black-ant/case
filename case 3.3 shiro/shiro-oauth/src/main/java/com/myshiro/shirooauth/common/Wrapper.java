package com.myshiro.shirooauth.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/2/22 22:47
 * @Version 1.0
 **/
@Data
public class Wrapper <T> implements Serializable {

    private static final long serialVersionUID = 4893280118017319089L;
    /**
     * 成功码.
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 成功信息.
     */
    public static final String SUCCESS_MESSAGE = "操作成功";

    /**
     * 错误码.
     */
    public static final int ERROR_CODE = 500;

    /**
     * 错误信息.
     */
    public static final String ERROR_MESSAGE = "内部异常";

    /**
     * 错误码：参数非法
     */
    public static final int ILLEGAL_CODE = 100;

    /**
     * 错误信息：参数非法
     */
    public static final String ILLEGAL_MESSAGE = "参数非法";

    /**
     * 编号.
     */
    private int code;

    /**
     * 信息.
     */
    private String message;

    /**
     * 结果数据
     */
    private T result;


    public Wrapper() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public  Wrapper(int code, String message) {
        this(code, message, null);
    }

    public Wrapper(int code, String message, T result) {
        super();
        this.code(code).message(message).result(result);
    }

    private Wrapper<T> code(int code) {
        this.setCode(code);
        return this;
    }

    private Wrapper<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public Wrapper<T> result(T result) {
        this.setResult(result);
        return this;
    }

}

