package com.gang.design.design3.command;

/**
 * @Classname Memento
 * @Description TODO
 * @Date 2020/12/13 14:42
 * @Created by zengzg
 */
public class CommandMain {

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command cmd = new MyCommand(receiver);
        Invoker invoker = new Invoker(cmd);
        invoker.action();
    }
}
