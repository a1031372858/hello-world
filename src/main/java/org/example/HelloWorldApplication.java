package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HelloWorldApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(HelloWorldApplication.class, args);
    }
}
