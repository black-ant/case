package com.gang.design.design3.mediator;

/**
 * @Classname User
 * @Description TODO
 * @Date 2020/12/13 14:15
 * @Created by zengzg
 */
public abstract class User {

    private Mediator mediator;

    public Mediator getMediator(){
        return mediator;
    }

    public User(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void work();
}
