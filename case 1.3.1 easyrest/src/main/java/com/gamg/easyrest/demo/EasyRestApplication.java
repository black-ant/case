package com.gamg.easyrest.demo;


import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Component
@ApplicationPath("/rest/")
public class EasyRestApplication extends Application {
}
