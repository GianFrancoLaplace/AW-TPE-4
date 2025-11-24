package org.example.cuentafacturacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CuentaFacturacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CuentaFacturacionApplication.class, args);
    }

}
