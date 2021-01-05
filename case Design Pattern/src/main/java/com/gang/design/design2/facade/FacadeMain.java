package com.gang.design.design2.facade;

/**
 * @Classname FacadeMain
 * @Description TODO
 * @Date 2020/12/15 23:26
 * @Created by zengzg
 */
public class FacadeMain {

    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.startup();
        computer.shutdown();
    }
}
