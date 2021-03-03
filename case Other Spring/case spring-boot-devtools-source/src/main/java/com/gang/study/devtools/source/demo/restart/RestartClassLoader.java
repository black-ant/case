package com.gang.study.devtools.source.demo.restart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.SmartClassLoader;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;

import com.gang.study.devtools.source.demo.restart.ClassLoaderFile.Kind;

/**
 * Disposable {@link ClassLoader} used to support application restarting. Provides parent
 * last loading for the specified URLs.
 *
 * @author Andy Clement
 * @author Phillip Webb
 * @since 1.3.0
 */
public class RestartClassLoader extends URLClassLoader implements SmartClassLoader {

    private final Log logger;

    private final ClassLoaderFileRepository updatedFiles;

    /**
     * Create a new {@link RestartClassLoader} instance.
     *
     * @param parent the parent classloader
     * @param urls   the urls managed by the classloader
     */
    public RestartClassLoader(ClassLoader parent, URL[] urls) {
        this(parent, urls, ClassLoaderFileRepository.NONE);
    }

    /**
     * Create a new {@link RestartClassLoader} instance.
     *
     * @param parent       the parent classloader
     * @param updatedFiles any files that have been updated since the JARs referenced in
     *                     URLs were created.
     * @param urls         the urls managed by the classloader
     */
    public RestartClassLoader(ClassLoader parent, URL[] urls, ClassLoaderFileRepository updatedFiles) {
        this(parent, urls, updatedFiles, LogFactory.getLog(RestartClassLoader.class));
    }

    /**
     * Create a new {@link RestartClassLoader} instance.
     *
     * @param parent       the parent classloader
     * @param updatedFiles any files that have been updated since the JARs referenced in
     *                     URLs were created.
     * @param urls         the urls managed by the classloader
     * @param logger       the logger used for messages
     */
    public RestartClassLoader(ClassLoader parent, URL[] urls, ClassLoaderFileRepository updatedFiles, Log logger) {
        super(urls, parent);
        Assert.notNull(parent, "Parent must not be null");
        Assert.notNull(updatedFiles, "UpdatedFiles must not be null");
        Assert.notNull(logger, "Logger must not be null");
        this.updatedFiles = updatedFiles;
        this.logger = logger;
        if (logger.isDebugEnabled()) {
            logger.debug("Created RestartClassLoader " + toString());
        }
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        // Use the parent since we're shadowing resource and we don't want duplicates
        Enumeration<URL> resources = getParent().getResources(name);
        ClassLoaderFile file = this.updatedFiles.getFile(name);
        if (file != null) {
            // Assume that we're replacing just the first item
            if (resources.hasMoreElements()) {
                resources.nextElement();
            }
            if (file.getKind() != Kind.DELETED) {
                return new CompoundEnumeration<>(createFileUrl(name, file), resources);
            }
        }
        return resources;
    }

    @Override
    public URL getResource(String name) {
        ClassLoaderFile file = this.updatedFiles.getFile(name);
        if (file != null && file.getKind() == Kind.DELETED) {
            return null;
        }
        URL resource = findResource(name);
        if (resource != null) {
            return resource;
        }
        return getParent().getResource(name);
    }

    @Override
    public URL findResource(String name) {
        final ClassLoaderFile file = this.updatedFiles.getFile(name);
        if (file == null) {
            return super.findResource(name);
        }
        if (file.getKind() == Kind.DELETED) {
            return null;
        }
        return AccessController.doPrivileged((PrivilegedAction<URL>) () -> createFileUrl(name, file));
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        String path = name.replace('.', '/').concat(".class");
        ClassLoaderFile file = this.updatedFiles.getFile(path);
        if (file != null && file.getKind() == Kind.DELETED) {
            throw new ClassNotFoundException(name);
        }
        synchronized (getClassLoadingLock(name)) {
            Class<?> loadedClass = findLoadedClass(name);
            if (loadedClass == null) {
                try {
                    loadedClass = findClass(name);
                } catch (ClassNotFoundException ex) {
                    loadedClass = getParent().loadClass(name);
                }
            }
            if (resolve) {
                resolveClass(loadedClass);
            }
            return loadedClass;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = name.replace('.', '/').concat(".class");
        final ClassLoaderFile file = this.updatedFiles.getFile(path);
        if (file == null) {
            return super.findClass(name);
        }
        if (file.getKind() == Kind.DELETED) {
            throw new ClassNotFoundException(name);
        }
        return AccessController.doPrivileged((PrivilegedAction<Class<?>>) () -> {
            byte[] bytes = file.getContents();
            return defineClass(name, bytes, 0, bytes.length);
        });
    }

    private URL createFileUrl(String name, ClassLoaderFile file) {
        try {
            return new URL("reloaded", null, -1, "/" + name, new ClassLoaderFileURLStreamHandler(file));
        } catch (MalformedURLException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Finalized classloader " + toString());
        }
        super.finalize();
    }

    @Override
    public boolean isClassReloadable(Class<?> classType) {
        return (classType.getClassLoader() instanceof RestartClassLoader);
    }

    /**
     * Compound {@link Enumeration} that adds an additional item to the front.
     */
    private static class CompoundEnumeration<E> implements Enumeration<E> {

        private E firstElement;

        private final Enumeration<E> enumeration;

        CompoundEnumeration(E firstElement, Enumeration<E> enumeration) {
            this.firstElement = firstElement;
            this.enumeration = enumeration;
        }

        @Override
        public boolean hasMoreElements() {
            return (this.firstElement != null || this.enumeration.hasMoreElements());
        }

        @Override
        public E nextElement() {
            if (this.firstElement == null) {
                return this.enumeration.nextElement();
            }
            E element = this.firstElement;
            this.firstElement = null;
            return element;
        }

    }

}

