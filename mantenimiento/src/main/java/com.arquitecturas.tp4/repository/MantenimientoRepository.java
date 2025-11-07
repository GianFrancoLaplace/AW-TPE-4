package com.arquitecturas.tp4.repository;

import com.arquitecturas.tp4.entities.Mantenimiento;
import com.arquitecturas.tp4.entities.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento,Long> {

    @Query("SELECT m FROM Mantenimiento m WHERE m.idMonopatin = :idMonopatin")

    Mantenimiento findByIdMonopatin(@Param("idMonopatin") int idMonopatin);
    @Modifying   // 👈 le indica a Spring que NO es un SELECT
    @Transactional
    @Query("UPDATE Mantenimiento SET fecha_fin = CURRENT_DATE WHERE id_mantenimiento = :id")
    void updateDate(@Param("id") int id);

}
