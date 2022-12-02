package org.velikokhatko.part3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;
import org.velikokhatko.part3.config.AnnotatedConfiguration;
import org.velikokhatko.part3.context.AnyService;

public class SpringAnnotatedApplication {

    public static void main(String[] args) {
        //запуск с VM options: --add-opens java.base/java.lang=ALL-UNNAMED
        AnnotationConfigApplicationContext annotatedContext = new AnnotationConfigApplicationContext(AnnotatedConfiguration.class);
        AnyService anyService = annotatedContext.getBean("anyServiceAnnotatedBean", AnyService.class);
        Assert.notNull(anyService, "Мы сюда даже не дойдём, если всё пизданётся");

        String hello = annotatedContext.getBean("sayHello", String.class);
        Assert.notNull(hello, "Мы сюда даже не дойдём, если всё пизданётся");
    }
}
