package org.example.tp4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.tp4", "controller"})
public class Tp4Application {

    public static void main(String[] args) {
        SpringApplication.run(Tp4Application.class, args);
    }

}
