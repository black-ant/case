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

        //���õ�һ��״̬
        state.setValue("state1");
        context.method();

        //���õڶ���״̬
        state.setValue("state2");
        context.method();
    }
}
