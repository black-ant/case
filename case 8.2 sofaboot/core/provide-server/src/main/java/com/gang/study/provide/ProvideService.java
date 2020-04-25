package com.gang.study.provide;

import com.gang.study.focase.demo.service.FacadeService;

/**
 * @Classname ProvideService
 * @Description TODO
 * @Date 2020/4/25 21:02
 * @Created by zengzg
 */
public class ProvideService implements FacadeService {

    private String message;

    public String message() {
        return message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
