package com.gang.study.devtools.source.demo.restart;

import java.util.Arrays;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * {@link UncaughtExceptionHandler} decorator that allows a thread to exit silently.
 *
 * @author Phillip Webb
 * @author Andy Wilkinson
 */
class SilentExitExceptionHandler implements UncaughtExceptionHandler {

    private final UncaughtExceptionHandler delegate;

    SilentExitExceptionHandler(UncaughtExceptionHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        if (exception instanceof SilentExitException) {
            if (isJvmExiting(thread)) {
                preventNonZeroExitCode();
            }
            return;
        }
        if (this.delegate != null) {
            this.delegate.uncaughtException(thread, exception);
        }
    }

    private boolean isJvmExiting(Thread exceptionThread) {
        for (Thread thread : getAllThreads()) {
            if (thread != exceptionThread && thread.isAlive() && !thread.isDaemon()) {
                return false;
            }
        }
        return true;
    }

    protected Thread[] getAllThreads() {
        ThreadGroup rootThreadGroup = getRootThreadGroup();
        Thread[] threads = new Thread[32];
        int count = rootThreadGroup.enumerate(threads);
        while (count == threads.length) {
            threads = new Thread[threads.length * 2];
            count = rootThreadGroup.enumerate(threads);
        }
        return Arrays.copyOf(threads, count);
    }

    private ThreadGroup getRootThreadGroup() {
        ThreadGroup candidate = Thread.currentThread().getThreadGroup();
        while (candidate.getParent() != null) {
            candidate = candidate.getParent();
        }
        return candidate;
    }

    protected void preventNonZeroExitCode() {
        System.exit(0);
    }

    static void setup(Thread thread) {
        UncaughtExceptionHandler handler = thread.getUncaughtExceptionHandler();
        if (!(handler instanceof SilentExitExceptionHandler)) {
            handler = new SilentExitExceptionHandler(handler);
            thread.setUncaughtExceptionHandler(handler);
        }
    }

    static void exitCurrentThread() {
        throw new SilentExitException();
    }

    private static class SilentExitException extends RuntimeException {

    }

}

