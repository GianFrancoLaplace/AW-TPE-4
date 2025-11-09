package com.viajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients(basePackages = "com.viajes.client")
public class ViajesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViajesApplication.class, args);
    }

}
