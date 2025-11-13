package org.example.cuentafacturacion.repository;

import org.example.cuentafacturacion.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query("UPDATE Cuenta c SET c.estado ='ANULADA'  WHERE c.idCuenta = :id")
    public void updateEstado(@Param("id") int id);

    @Query("UPDATE Cuenta c SET c.categoria = :categoria  WHERE c.idCuenta = :id")
    public void updatePlan(@Param("id") int id, @Param("categoria") String categoria);
}
