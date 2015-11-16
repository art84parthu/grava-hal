package com.bol.assignment.grava.hal;

import com.bol.assignment.grava.hal.config.WebConfig;
import com.bol.assignment.grava.hal.service.impl.GravaHalService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.bol.assignment.grava.hal"})
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);
        new WebConfig(ctx.getBean(GravaHalService.class));
        ctx.registerShutdownHook();
        ;
    }
}
