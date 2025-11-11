package org.example.tarifa.repository;

import org.example.tarifa.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa,Long> {
    //trae la tarifa que tiene true en activa
    Optional<Tarifa> findByActivaTrue();


    @Transactional
    @Modifying
    @Query("UPDATE Tarifa t SET t.activa = false WHERE t.activa = true")
    void desactivarTodas();
}
