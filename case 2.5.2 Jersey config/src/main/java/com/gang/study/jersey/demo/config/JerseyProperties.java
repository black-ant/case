package com.gang.study.jersey.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname JerseyProperties
 * @Description TODO
 * @Date 2020/4/23 15:12
 * @Created by zengzg
 */
@ConfigurationProperties(
        prefix = "com.gang.jersey"
)
@Component
@Data
public class JerseyProperties {

    private boolean multiPartFeature = false;
    private List<String> jerseyPackage = new ArrayList();
    private String applicationName;
}
