package com.gang.web.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.util.PathMatcher;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.util.UrlPathHelper;

import java.util.Map;
import java.util.function.Predicate;

/**
 * @Classname HandlerInterceptorsConfig
 * @Description TODO
 * @Date 2022/10/8
 * @Created by zengzg
 */
@Configuration
public class DefaultRequestMappingConfig {

    @Bean
    @Primary
    public RequestMappingHandlerMapping requestMappingHandlerMapping(
            @Qualifier("mvcContentNegotiationManager") ContentNegotiationManager contentNegotiationManager,
            @Qualifier("mvcConversionService") FormattingConversionService conversionService,
            @Qualifier("mvcResourceUrlProvider") ResourceUrlProvider resourceUrlProvider) {

        RequestMappingHandlerMapping mapping = createRequestMappingHandlerMapping();
        mapping.setOrder(0);
//        mapping.setInterceptors(getInterceptors(conversionService, resourceUrlProvider));
        mapping.setContentNegotiationManager(contentNegotiationManager);
//        mapping.setCorsConfigurations(getCorsConfigurations());

        PathMatchConfigurer configurer = new PathMatchConfigurer();

        Boolean useSuffixPatternMatch = configurer.isUseSuffixPatternMatch();
        if (useSuffixPatternMatch != null) {
            mapping.setUseSuffixPatternMatch(useSuffixPatternMatch);
        }
        Boolean useRegisteredSuffixPatternMatch = configurer.isUseRegisteredSuffixPatternMatch();
        if (useRegisteredSuffixPatternMatch != null) {
            mapping.setUseRegisteredSuffixPatternMatch(useRegisteredSuffixPatternMatch);
        }
        Boolean useTrailingSlashMatch = configurer.isUseTrailingSlashMatch();
        if (useTrailingSlashMatch != null) {
            mapping.setUseTrailingSlashMatch(useTrailingSlashMatch);
        }

        UrlPathHelper pathHelper = configurer.getUrlPathHelper();
        if (pathHelper != null) {
            mapping.setUrlPathHelper(pathHelper);
        }
        PathMatcher pathMatcher = configurer.getPathMatcher();
        if (pathMatcher != null) {
            mapping.setPathMatcher(pathMatcher);
        }
        return mapping;
    }

    protected RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

}
