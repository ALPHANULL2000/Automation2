package com.example.automation2;

import com.example.automation2.model.TestModel;
import com.example.automation2.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;



@SpringBootApplication
public class Automation2Application {

    private final static Logger logger = LoggerFactory.getLogger(Automation2Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Automation2Application.class, args);
        AbstractApplicationContext context
                = new ClassPathXmlApplicationContext("contexts/ApplicationContext.xml");
        TestModel testModel = context.getBean("testModel2",TestModel.class);
        User testUser = context.getBean("testUser",User.class);
        System.out.println(testModel+"\n"+testUser);
        context.close();
        logger.info("SpringBoot Server Started - testModel and ApplicationContext.xml compiled successfully");
        logger.info("UUID For (phpMyAdmin) Inserting : "+ UUID.randomUUID());
    }
}


