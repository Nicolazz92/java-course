package org.velikokhatko.part3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChildAnnotatedConfiguration {

    @Bean
    public String sayHello() {
        return "Hello";
    }
}
