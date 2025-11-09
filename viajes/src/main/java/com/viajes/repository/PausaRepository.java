package com.viajes.repository;

import com.viajes.entity.Pausa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PausaRepository extends JpaRepository<Pausa, Long> {

    // Buscar pausas de un viaje
    List<Pausa> findByViajeId(Long idViaje);

    // Buscar pausa activa
    Optional<Pausa> findByViajeIdAndActivaTrue(Long idViaje);

    // Pausas extendidas
    List<Pausa> findByViajeIdAndExtendidaTrue(Long idViaje);

    // Calcular total de minutos pausados en un viaje
    @Query(value = "SELECT SUM(DATEDIFF('MINUTE', inicio_pausa, fin_pausa)) FROM pausa WHERE id_viaje = :idViaje", nativeQuery = true)
    Integer calcularTotalMinutosPausas(@Param("idViaje") Long idViaje);

    // Detectar si hubo alguna pausa excesiva
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Pausa p WHERE p.viaje.id = :idViaje AND p.extendida = true")
    boolean tienePausaExcesiva(@Param("idViaje") Long idViaje);
}
