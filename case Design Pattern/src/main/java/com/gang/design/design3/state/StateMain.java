package com.gang.design.design3.state;

/**
 * @Classname StateMain
 * @Description TODO
 * @Date 2020/12/13 14:39
 * @Created by zengzg
 */
public class StateMain {
    public static void main(String[] args) {

        State state = new State();
        Context context = new Context(state);

        //设置第一种状态
        state.setValue("state1");
        context.method();

        //设置第二种状态
        state.setValue("state2");
        context.method();
    }
}
