/*
package org.example.cuentafacturacion.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimiento;

    @ManyToOne
    @JoinColumn(name = "idCuenta", nullable = false)
    private Cuenta cuenta;

    private float monto;

    private String descripcion;

    private LocalDateTime fechaMovimiento;

    private String referencia;
}

 */
