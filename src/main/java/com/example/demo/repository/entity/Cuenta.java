package com.example.demo.repository.entity;

import java.util.Objects;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String banco;
    private String sucursal;
    private String dc;

    @Column(name = "numerocuenta")
    private String numeroCuenta;
    @Column(name = "saldoactual")
    private float saldoActual;

    @ManyToOne
    @JoinColumn(name = "idcliente")
    private Cliente cliente;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cuenta other = (Cuenta) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}