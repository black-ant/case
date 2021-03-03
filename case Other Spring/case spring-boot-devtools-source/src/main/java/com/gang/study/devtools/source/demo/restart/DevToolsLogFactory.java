package com.gang.study.devtools.source.demo.restart;

import org.apache.commons.logging.Log;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.context.ApplicationListener;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Devtools deferred logging support.
 *
 * @author Phillip Webb
 * @since 2.1.0
 */
public final class DevToolsLogFactory {

    private static final Map<Log, Class<?>> logs = new LinkedHashMap<>();

    private DevToolsLogFactory() {
    }

    /**
     * Get a {@link Log} instance for the specified source that will be automatically
     * {@link DeferredLog#switchTo(Class) switched} when the
     * {@link ApplicationPreparedEvent context is prepared}.
     * @param source the source for logging
     * @return a {@link DeferredLog} instance
     */
    public static Log getLog(Class<?> source) {
        synchronized (logs) {
            Log log = new DeferredLog();
            logs.put(log, source);
            return log;
        }
    }

    /**
     * Listener used to log and switch when the context is ready.
     */
    static class Listener implements ApplicationListener<ApplicationPreparedEvent> {

        @Override
        public void onApplicationEvent(ApplicationPreparedEvent event) {
            synchronized (logs) {
                logs.forEach((log, source) -> {
                    if (log instanceof DeferredLog) {
                        ((DeferredLog) log).switchTo(source);
                    }
                });
                logs.clear();
            }
        }

    }

}
