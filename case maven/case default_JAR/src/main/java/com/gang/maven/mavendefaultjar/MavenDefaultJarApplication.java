package com.gang.maven.mavendefaultjar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.gang"})
public class MavenDefaultJarApplication {



    public static void main(String[] args) {
        SpringApplication.run(MavenDefaultJarApplication.class, args);
    }

}
