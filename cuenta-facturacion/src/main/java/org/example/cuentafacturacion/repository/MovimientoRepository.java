package org.example.cuentafacturacion.repository;

import org.example.cuentafacturacion.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.idCuenta = :idCuenta " +
            "AND m.fechaMovimiento BETWEEN :fechaInicio AND :fechaFin")
    List<Movimiento> findByCuentaAndFecha(
            @Param("idCuenta") Long idCuenta,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    @Query("SELECT m FROM Movimiento m WHERE m.fechaMovimiento BETWEEN :fechaInicio AND :fechaFin")
    List<Movimiento> findByFecha(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
}