package com.gang.study.devtools.source.demo.restart;

import org.apache.commons.logging.Log;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * DevTools settings loaded from {@literal /META-INF/spring-devtools.properties} files.
 *
 * @author Phillip Webb
 * @since 1.3.0
 */
public class DevToolsSettings {

    /**
     * The location to look for settings properties. Can be present in multiple JAR files.
     */
    public static final String SETTINGS_RESOURCE_LOCATION = "META-INF/spring-devtools.properties";

    private static final Log logger = DevToolsLogFactory.getLog(DevToolsSettings.class);

    private static DevToolsSettings settings;

    private final List<Pattern> restartIncludePatterns = new ArrayList<>();

    private final List<Pattern> restartExcludePatterns = new ArrayList<>();

    DevToolsSettings() {
    }

    void add(Map<?, ?> properties) {
        Map<String, Pattern> includes = getPatterns(properties, "restart.include.");
        this.restartIncludePatterns.addAll(includes.values());
        Map<String, Pattern> excludes = getPatterns(properties, "restart.exclude.");
        this.restartExcludePatterns.addAll(excludes.values());
    }

    private Map<String, Pattern> getPatterns(Map<?, ?> properties, String prefix) {
        Map<String, Pattern> patterns = new LinkedHashMap<>();
        properties.forEach((key, value) -> {
            String name = String.valueOf(key);
            if (name.startsWith(prefix)) {
                Pattern pattern = Pattern.compile((String) value);
                patterns.put(name, pattern);
            }
        });
        return patterns;
    }

    public boolean isRestartInclude(URL url) {
        return isMatch(url.toString(), this.restartIncludePatterns);
    }

    public boolean isRestartExclude(URL url) {
        return isMatch(url.toString(), this.restartExcludePatterns);
    }

    private boolean isMatch(String url, List<Pattern> patterns) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(url).find()) {
                return true;
            }
        }
        return false;
    }

    public static DevToolsSettings get() {
        if (settings == null) {
            settings = load();
        }
        return settings;
    }

    static DevToolsSettings load() {
        return load(SETTINGS_RESOURCE_LOCATION);
    }

    static DevToolsSettings load(String location) {
        try {
            DevToolsSettings settings = new DevToolsSettings();
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(location);
            while (urls.hasMoreElements()) {
                settings.add(PropertiesLoaderUtils.loadProperties(new UrlResource(urls.nextElement())));
            }
            if (logger.isDebugEnabled()) {
                logger.debug("Included patterns for restart : " + settings.restartIncludePatterns);
                logger.debug("Excluded patterns for restart : " + settings.restartExcludePatterns);
            }
            return settings;
        }
        catch (Exception ex) {
            throw new IllegalStateException("Unable to load devtools settings from location [" + location + "]", ex);
        }
    }

}
