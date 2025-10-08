package com.gang.web.demo.to;

public class TestDemo {


    public ConfigurableApplicationContext run(String... args) {

            // 1. 配置无头属性（通常用于设置系统属性，特别是在没有图形界面的环境中）
            configureHeadlessProperty();

            // 2. 获取并初始化运行监听器
            // 运行监听器用于在Spring应用启动的各个阶段触发事件
            SpringApplicationRunListeners listeners = getRunListeners(args);
            // 触发启动开始事件
            listeners.starting();

            // 3. 解析并准备应用启动参数
            ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);

            // 4. 准备应用运行环境
            // 包括：加载配置、设置profile、系统环境变量等
            ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);

            // 5. 配置忽略Bean信息（处理某些特定的Bean信息处理策略）
            configureIgnoreBeanInfo(environment);

            // 6. 打印应用启动Banner
            Banner printedBanner = printBanner(environment);

            // 7. 创建应用上下文
            // 根据应用类型（Web/普通）创建不同的上下文
            context = createApplicationContext();

            // 8. 准备应用上下文
            // 包括：设置环境、注册初始化器、加载source、处理前置处理器等
            prepareContext(context, environment, listeners, applicationArguments, printedBanner);

            // 9. 刷新应用上下文
            // 最关键的步骤：
            // - 调用Bean工厂后置处理器
            // - 注册Bean定义
            // - 实例化非懒加载的单例Bean
            // - 完成BeanFactory的初始化
            refreshContext(context);

            // 10. 上下文刷新后的额外处理
            // 用于执行一些自定义的启动后逻辑
            afterRefresh(context, applicationArguments);

            // 11. 停止性能监控计时
            stopWatch.stop();

            // 12. 如果开启了启动日志记录，则记录启动信息
            if (this.logStartupInfo) {
                new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
            }

            // 13. 触发上下文启动完成事件
            listeners.started(context);

            // 14. 执行所有注册的运行器（ApplicationRunner和CommandLineRunner）
            callRunners(context, applicationArguments);

            // 15. 触发应用运行事件
            // 表示应用已完全启动并准备接收请求
            listeners.running(context);

    }
}
