package com.gang.spring.exceptionhandle.demo;

import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
@Component
public class JWSApplication extends Application {
}
