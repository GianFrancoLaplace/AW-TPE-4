package org.example.cuentafacturacion.repository;

import org.example.cuentafacturacion.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaUsuarioRepository extends JpaRepository<Cuenta, Long> {

    @Modifying
    @Query(value = "INSERT INTO cuenta_usuario (cuenta_id, usuario_id) VALUES (:cuentaId, :usuarioId)", nativeQuery = true)
    void asociarUsuario(int cuentaId, int usuarioId);

    @Modifying
    @Query(value = "DELETE FROM cuenta_usuario WHERE cuenta_id = :cuentaId AND usuario_id = :usuarioId", nativeQuery = true)
    void quitarUsuario(int cuentaId, int usuarioId);

    @Query(value = "SELECT usuario_id FROM cuenta_usuario WHERE cuenta_id = :cuentaId", nativeQuery = true)
    List<Integer> obtenerUsuarios(int cuentaId);
}
