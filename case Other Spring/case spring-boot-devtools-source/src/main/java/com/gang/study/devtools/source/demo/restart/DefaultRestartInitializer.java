package com.gang.study.devtools.source.demo.restart;

import java.net.URL;

/**
 * Default {@link RestartInitializer} that only enable initial restart when running a
 * standard "main" method. Skips initialization when running "fat" jars (included
 * exploded) or when running from a test.
 *
 * @author Phillip Webb
 * @author Andy Wilkinson
 * @since 1.3.0
 */
public class DefaultRestartInitializer implements RestartInitializer {

    @Override
    public URL[] getInitialUrls(Thread thread) {
        if (!isMain(thread)) {
            return null;
        }

        //
//        if (!DevToolsEnablementDeducer.shouldEnable(thread)) {
//            return null;
//        }
        return getUrls(thread);
    }

    /**
     * Returns if the thread is for a main invocation. By default checks the name of the
     * thread and the context classloader.
     * @param thread the thread to check
     * @return {@code true} if the thread is a main invocation
     */
    protected boolean isMain(Thread thread) {
        return thread.getName().equals("main")
                && thread.getContextClassLoader().getClass().getName().contains("AppClassLoader");
    }

    /**
     * Return the URLs that should be used with initialization.
     * @param thread the source thread
     * @return the URLs
     */
    protected URL[] getUrls(Thread thread) {
        return ChangeableUrls.fromClassLoader(thread.getContextClassLoader()).toArray();
    }

}

