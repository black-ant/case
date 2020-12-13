package com.gang.design.design3.chain;

/**
 * @Classname AbstractHandler
 * @Description TODO
 * @Date 2020/12/13 14:45
 * @Created by zengzg
 */
public abstract class AbstractHandler {

    private Handler handler;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

}
