package com.gang.processor.demo.proxy;

/**
 * @Classname AopProxyImpl
 * @Description TODO
 * @Date 2021/10/5
 * @Created by zengzg
 */
public class AopProxyImpl extends Source {

    private Sourceable source;

    public AopProxyImpl(Sourceable source) {
        super();
        // 注意 ,代理模式是在代理类中创建一个对象
        // this.source = new Source();

        // 修饰模式是传入对应的对象
        this.source = source;
    }

    @Override
    public void method() {
        before();
        source.method();
        atfer();
    }

    private void atfer() {
        System.out.println("after proxy!");
    }

    private void before() {
        System.out.println("before proxy!");
    }
}
