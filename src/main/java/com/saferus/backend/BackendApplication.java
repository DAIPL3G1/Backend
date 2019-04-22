package com.saferus.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.saferus.backend")
@SpringBootApplication()
public class BackendApplication extends SpringBootServletInitializer  {
    
    private static Class applicationClass = BackendApplication.class;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
