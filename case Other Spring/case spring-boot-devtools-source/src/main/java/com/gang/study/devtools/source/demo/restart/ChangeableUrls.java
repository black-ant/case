package com.gang.study.devtools.source.demo.restart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Stream;

/**
 * A filtered collection of URLs which can change after the application has started.
 *
 * @author Phillip Webb
 * @author Andy Wilkinson
 */
final class ChangeableUrls implements Iterable<URL> {

    private static Logger logger = LoggerFactory.getLogger(ChangeableUrls.class);

    private final List<URL> urls;

    private ChangeableUrls(URL... urls) {
        DevToolsSettings settings = DevToolsSettings.get();
        List<URL> reloadableUrls = new ArrayList<>(urls.length);
        boolean reloadable = false;
        for (URL url : urls) {
            reloadable =
                    (settings.isRestartInclude(url) || isFolderUrl(url.toString())) && !settings.isRestartExclude(url);
            if (reloadable) {
                reloadableUrls.add(url);
            }
        }
        logger.info("Matching URLs for reloading : " + reloadableUrls);
        this.urls = Collections.unmodifiableList(reloadableUrls);
    }

    private boolean isFolderUrl(String urlString) {
        return urlString.startsWith("file:") && urlString.endsWith("/");
    }

    @Override
    public Iterator<URL> iterator() {
        return this.urls.iterator();
    }

    int size() {
        return this.urls.size();
    }

    URL[] toArray() {
        return this.urls.toArray(new URL[0]);
    }

    List<URL> toList() {
        return Collections.unmodifiableList(this.urls);
    }

    @Override
    public String toString() {
        return this.urls.toString();
    }

    static ChangeableUrls fromClassLoader(ClassLoader classLoader) {
        List<URL> urls = new ArrayList<>();
        for (URL url : urlsFromClassLoader(classLoader)) {
            urls.add(url);
            urls.addAll(getUrlsFromClassPathOfJarManifestIfPossible(url));
        }
        return fromUrls(urls);
    }

    private static URL[] urlsFromClassLoader(ClassLoader classLoader) {
        if (classLoader instanceof URLClassLoader) {
            return ((URLClassLoader) classLoader).getURLs();
        }
        return Stream.of(ManagementFactory.getRuntimeMXBean().getClassPath().split(File.pathSeparator))
                .map(ChangeableUrls::toUrl).toArray(URL[]::new);
    }

    private static URL toUrl(String classPathEntry) {
        try {
            return new File(classPathEntry).toURI().toURL();
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException("URL could not be created from '" + classPathEntry + "'", ex);
        }
    }

    private static List<URL> getUrlsFromClassPathOfJarManifestIfPossible(URL url) {
        try {
            File file = new File(url.toURI());
            if (file.isFile()) {
                try (JarFile jarFile = new JarFile(file)) {
                    try {
                        return getUrlsFromManifestClassPathAttribute(url, jarFile);
                    } catch (IOException ex) {
                        throw new IllegalStateException(
                                "Failed to read Class-Path attribute from manifest of jar " + url, ex);
                    }
                }
            }
        } catch (Exception ex) {
            // Assume it's not a jar and continue
        }
        return Collections.emptyList();
    }

    private static List<URL> getUrlsFromManifestClassPathAttribute(URL jarUrl, JarFile jarFile) throws IOException {
        Manifest manifest = jarFile.getManifest();
        if (manifest == null) {
            return Collections.emptyList();
        }
        String classPath = manifest.getMainAttributes().getValue(Attributes.Name.CLASS_PATH);
        if (!StringUtils.hasText(classPath)) {
            return Collections.emptyList();
        }
        String[] entries = StringUtils.delimitedListToStringArray(classPath, " ");
        List<URL> urls = new ArrayList<>(entries.length);
        List<URL> nonExistentEntries = new ArrayList<>();
        for (String entry : entries) {
            try {
                URL referenced = new URL(jarUrl, entry);
                if (new File(referenced.getFile()).exists()) {
                    urls.add(referenced);
                } else {
                    referenced = new URL(jarUrl, URLDecoder.decode(entry, "UTF-8"));
                    if (new File(referenced.getFile()).exists()) {
                        urls.add(referenced);
                    } else {
                        nonExistentEntries.add(referenced);
                    }
                }
            } catch (MalformedURLException ex) {
                throw new IllegalStateException("Class-Path attribute contains malformed URL", ex);
            }
        }
        if (!nonExistentEntries.isEmpty()) {
            logger.info("The Class-Path manifest attribute in " + jarFile.getName()
                    + " referenced one or more files that do not exist: "
                    + StringUtils.collectionToCommaDelimitedString(nonExistentEntries));
        }
        return urls;
    }

    static ChangeableUrls fromUrls(Collection<URL> urls) {
        return fromUrls(new ArrayList<>(urls).toArray(new URL[urls.size()]));
    }

    static ChangeableUrls fromUrls(URL... urls) {
        return new ChangeableUrls(urls);
    }

}
