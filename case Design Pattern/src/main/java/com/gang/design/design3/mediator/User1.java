package com.gang.design.design3.mediator;

/**
 * @Classname User1
 * @Description TODO
 * @Date 2020/12/13 14:16
 * @Created by zengzg
 */
public class User1 extends User {

    public User1(Mediator mediator){
        super(mediator);
    }

    @Override
    public void work() {
        System.out.println("user1 exe!");
    }
}
