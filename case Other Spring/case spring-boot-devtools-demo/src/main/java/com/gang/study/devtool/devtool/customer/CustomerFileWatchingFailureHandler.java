package com.gang.study.devtool.devtool.customer;

import org.springframework.boot.devtools.classpath.ClassPathFolders;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileChangeListener;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.boot.devtools.filewatch.FileSystemWatcherFactory;
import org.springframework.boot.devtools.restart.FailureHandler;
import org.springframework.boot.devtools.restart.Restarter;

import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @Classname FileWatchingFailureHandler
 * @Description TODO
 * @Date 2021/3/1 14:51
 * @Created by zengzg
 */
public class CustomerFileWatchingFailureHandler implements FailureHandler {

    private final FileSystemWatcherFactory fileSystemWatcherFactory;

    CustomerFileWatchingFailureHandler(FileSystemWatcherFactory fileSystemWatcherFactory) {
        this.fileSystemWatcherFactory = fileSystemWatcherFactory;
    }

    @Override
    public Outcome handle(Throwable failure) {
        CountDownLatch latch = new CountDownLatch(1);
        FileSystemWatcher watcher = this.fileSystemWatcherFactory.getFileSystemWatcher();
        watcher.addSourceFolders(
                new ClassPathFolders(Restarter.getInstance().getInitialUrls()));
        watcher.addListener(new Listener(latch));
        watcher.start();
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return Outcome.RETRY;
    }

    private static class Listener implements FileChangeListener {

        private final CountDownLatch latch;

        Listener(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onChange(Set<ChangedFiles> changeSet) {
            this.latch.countDown();
        }

    }

}

