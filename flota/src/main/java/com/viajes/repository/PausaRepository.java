package com.viajes.repository;

import com.viajes.entity.Pausa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PausaRepository extends JpaRepository<Pausa, Long> {
    List<Pausa> obtenerPorHorario(LocalDateTime fechaHoraInicio, LocalDateTime horarioActual);
}
