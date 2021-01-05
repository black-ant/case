package com.gang.design.design3.template;

/**
 * @Classname StrategtMain
 * @Description TODO
 * @Date 2020/12/13 14:56
 * @Created by zengzg
 */
public class TemplateMain {

    public static void main(String[] args) {
        String exp = "8+8";
        AbstractCalculator cal = new Plus();
        int result = cal.calculate(exp, "\\+");
        System.out.println(result);
    }
}
