package com.gang.study.source.demo.source;


import org.springframework.boot.SpringApplication;

/**
 * @Classname SpringApplicationSourceSource
 * @Description TODO
 * @Date 2020/7/8 16:22
 * @Created by zengzg
 */
public class SpringApplicationSource extends SpringApplication {


//    /**
//     * Run the Spring application, creating and refreshing a new
//     * {@link ApplicationContext}.
//     * @param args the application arguments (usually passed from a Java main method)
//     * @return a running {@link ApplicationContext}
//     */
//    public ConfigurableApplicationContext run(String... args) throws Exception {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        ConfigurableApplicationContext context = null;
//        Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
//        configureHeadlessProperty();
//        SpringApplicationRunListeners listeners = getRunListeners(args);
//        listeners.starting();
//        try {
//            ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
//            ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
//            configureIgnoreBeanInfo(environment);
//            Banner printedBanner = printBanner(environment);
//            context = createApplicationContext();
//            exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,
//                    new Class[] { ConfigurableApplicationContext.class }, context);
//            prepareContext(context, environment, listeners, applicationArguments, printedBanner);
//            refreshContext(context);
//            afterRefresh(context, applicationArguments);
//            stopWatch.stop();
//            if (this.logStartupInfo) {
//                new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
//            }
//            listeners.started(context);
//            callRunners(context, applicationArguments);
//        }
//        catch (Throwable ex) {
//            handleRunFailure(context, ex, exceptionReporters, listeners);
//            throw new IllegalStateException(ex);
//        }
//
//        try {
//            listeners.running(context);
//        }
//        catch (Throwable ex) {
//            handleRunFailure(context, ex, exceptionReporters, null);
//            throw new IllegalStateException(ex);
//        }
//        return context;
//    }

}

