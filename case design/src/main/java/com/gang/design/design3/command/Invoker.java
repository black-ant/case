package com.gang.design.design3.command;

/**
 * @Classname Invoker
 * @Description TODO
 * @Date 2020/12/13 14:42
 * @Created by zengzg
 */
public class Invoker {

    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void action(){
        command.exe();
    }
}

