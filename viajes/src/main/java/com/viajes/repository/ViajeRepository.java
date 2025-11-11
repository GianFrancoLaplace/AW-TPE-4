package com.viajes.repository;

import com.viajes.entity.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Long> {

    // Buscar viajes de un usuario
    List<Viaje> findByIdUsuario(Long idUsuario);

    // Buscar viajes de una cuenta
    List<Viaje> findByIdCuenta(Long idCuenta);

    // Buscar viajes en curso
    List<Viaje> findByEstadoViaje(String estadoViaje);

    // Reporte: viajes entre fechas
    List<Viaje> findByInicioBetween(LocalDateTime inicio, LocalDateTime fin);

    // Reporte: viajes de un monopatín en cierto año
    @Query("SELECT v FROM Viaje v WHERE v.idMonopatin = :idMonopatin AND YEAR(v.inicio) = :anio")
    List<Viaje> findByMonopatinAndAnio(String idMonopatin, int anio);

    // Reporte: total facturado entre fechas
    @Query("SELECT SUM(v.costoTotal) FROM Viaje v WHERE v.inicio BETWEEN :inicio AND :fin")
    Double calcularTotalFacturado(LocalDateTime inicio, LocalDateTime fin);
}