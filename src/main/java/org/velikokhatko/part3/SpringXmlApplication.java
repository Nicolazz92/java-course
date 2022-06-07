package org.velikokhatko.part3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;
import org.velikokhatko.part3.context.AnyService;

public class SpringXmlApplication {

    public static void main(String[] args) {
        ApplicationContext xmlBasedContext = new ClassPathXmlApplicationContext("xml-based-app-config.xml");
        AnyService anyService = (AnyService) xmlBasedContext.getBean("anyServiceXml");
        Assert.notNull(anyService, "Мы сюда даже не дойдём, если всё пизданётся");

        ApplicationContext componentScanBasedContext = new ClassPathXmlApplicationContext("annotation-based-app-config.xml");
        anyService = (AnyService) componentScanBasedContext.getBean("anyServiceAnnotated");
        Assert.notNull(anyService, "Мы сюда даже не дойдём, если всё пизданётся");
    }
}
