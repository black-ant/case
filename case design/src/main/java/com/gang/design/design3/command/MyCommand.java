package com.gang.design.design3.command;

/**
 * @Classname MyCommand
 * @Description TODO
 * @Date 2020/12/13 14:42
 * @Created by zengzg
 */

public class MyCommand implements Command {

    private Receiver receiver;

    public MyCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void exe() {
        receiver.action();
    }
}
