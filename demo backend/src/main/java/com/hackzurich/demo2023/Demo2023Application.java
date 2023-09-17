package com.hackzurich.demo2023;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Demo2023Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Demo2023Application.class);
        application.setAdditionalProfiles("ssl");
        application.run(args);

    }

}
