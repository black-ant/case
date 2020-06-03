package com.gang.study.security.saml.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.saml.SAMLEntryPoint;
import org.springframework.security.saml.websso.WebSSOProfileOptions;

/**
 * @Classname SAMLConfiguration
 * @Description TODO
 * @Date 2020/6/3 12:39
 * @Created by zengzg
 */
@Configuration
public class SAMLConfiguration {

    @Bean
    public SAMLEntryPoint getSamlEntryPoint(WebSSOProfileOptions webSSOProfileOptions) {
        SAMLEntryPoint samlEntryPoint = new SAMLEntryPoint();
        samlEntryPoint.setDefaultProfileOptions(webSSOProfileOptions);
        return samlEntryPoint;
    }

    @Bean
    public WebSSOProfileOptions webSSOProfileOptions() {
        WebSSOProfileOptions options = new WebSSOProfileOptions();
        options.setBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        options.setIncludeScoping(Boolean.FALSE);
        return options;
    }
}
