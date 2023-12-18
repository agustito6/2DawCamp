package com.example.demo.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "direcciones")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String pais;
    private String cp;
    @ManyToMany(cascade = { CascadeType.ALL }, mappedBy = "listaDirecciones")
    @ToString.Exclude
    private List<Cliente> listaClientes;
    public Direccion() {
        super();
        this.listaClientes = new ArrayList<Cliente>();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Direccion other = (Direccion) obj;
        return Objects.equals(id, other.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}