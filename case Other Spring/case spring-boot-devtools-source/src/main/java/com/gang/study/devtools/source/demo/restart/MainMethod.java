package com.gang.study.devtools.source.demo.restart;

import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * The "main" method located from a running thread.
 *
 * @author Phillip Webb
 */
class MainMethod {

    private final Method method;

    MainMethod() {
        this(Thread.currentThread());
    }

    MainMethod(Thread thread) {
        Assert.notNull(thread, "Thread must not be null");
        this.method = getMainMethod(thread);
    }

    private Method getMainMethod(Thread thread) {
        for (StackTraceElement element : thread.getStackTrace()) {
            if ("main".equals(element.getMethodName())) {
                Method method = getMainMethod(element);
                if (method != null) {
                    return method;
                }
            }
        }
        throw new IllegalStateException("Unable to find main method");
    }

    private Method getMainMethod(StackTraceElement element) {
        try {
            Class<?> elementClass = Class.forName(element.getClassName());
            Method method = elementClass.getDeclaredMethod("main", String[].class);
            if (Modifier.isStatic(method.getModifiers())) {
                return method;
            }
        }
        catch (Exception ex) {
            // Ignore
        }
        return null;
    }

    /**
     * Returns the actual main method.
     * @return the main method
     */
    Method getMethod() {
        return this.method;
    }

    /**
     * Return the name of the declaring class.
     * @return the declaring class name
     */
    String getDeclaringClassName() {
        return this.method.getDeclaringClass().getName();
    }

}
