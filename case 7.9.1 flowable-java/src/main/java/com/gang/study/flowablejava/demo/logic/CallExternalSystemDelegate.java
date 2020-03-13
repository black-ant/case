package com.gang.study.flowablejava.demo.logic;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @Classname CallExternalSystemDelegate
 * @Description TODO
 * @Date 2020/3/13 23:08
 * @Created by zengzg
 */
public class CallExternalSystemDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Calling the external system for employee "
                + execution.getVariable("employee"));
    }
}
