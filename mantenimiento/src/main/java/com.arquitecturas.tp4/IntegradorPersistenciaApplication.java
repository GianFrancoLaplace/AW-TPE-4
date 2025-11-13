package com.arquitecturas.tp4;

import com.arquitecturas.tp4.entities.Mantenimiento;
import com.arquitecturas.tp4.service.MantenimientoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootApplication
public class IntegradorPersistenciaApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(IntegradorPersistenciaApplication.class, args);

      //  Mantenimiento nuevo =new  Mantenimiento();

        //nuevo.setId_mantenimiento(1); // o dejar que lo genere la BD si es autoincremental
        //nuevo.setFechaInicio(Date.valueOf(LocalDate.now()));
        //nuevo.setFecha_fin(null); // todavía no finalizó
        //nuevo.setIdEncargado(10);
        //nuevo.setIdMonopatin(3);

        MantenimientoService ms = context.getBean(MantenimientoService.class);
        //ms.addMantenimiento(nuevo);
        ms.terminarMantiniminento(3);
        System.out.println("Mantenimiento insertado correctamente");

    }
}
