package com.gang.design.design3.mediator;

/**
 * @Classname User2
 * @Description TODO
 * @Date 2020/12/13 14:17
 * @Created by zengzg
 */
public class User2 extends User {

    public User2(Mediator mediator){
        super(mediator);
    }

    @Override
    public void work() {
        System.out.println("user2 exe!");
    }
}
