package org.velikokhatko.part3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.velikokhatko.part3.context.AnyService;

@Configuration
@Import(ChildAnnotatedConfiguration.class)
public class AnnotatedConfiguration {

    @Bean
    public AnyService anyServiceAnnotatedBean() {
        return new AnyService();
    }
}
