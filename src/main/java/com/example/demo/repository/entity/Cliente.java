package com.example.demo.repository.entity; 

import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="clientes")
public class Cliente {    
    @Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)   
    private Long id;     
    
    @Column(name = "nif")   
    private String nif;    
    
    @Column(name = "nombre")  
    private String nombre;    
    
    @Column(name = "apellidos")  
    private String apellidos;    
    
    @Column(name = "claveseguridad")  
    private String claveSeguridad;    
    
    @Column(name = "email")  
    private String email;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Cuenta> listaCuentas;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    // Tabla que mantiene la relacion N-N
    @JoinTable(
            // Nombre de la tabla
            name = "clientesdirecciones",
            // columna que almacena el id de cliente en la tabla clientesdirecciones
            joinColumns = @JoinColumn(name = "idcliente"),
            // columna que almacena el id de la direccion en la tabla clientesdirecciones
            inverseJoinColumns = @JoinColumn(name = "iddireccion"))
    @ToString.Exclude
    private List<Direccion> listaDirecciones;

    @Override  
    public boolean equals(Object obj) {    
        if (this == obj)      
            return true;    
        if (obj == null)      
            return false;    
        if (getClass() != obj.getClass())      
            return false;    
        Cliente other = (Cliente) obj;    
        return Objects.equals(id, other.id);  
    }    
    
    @Override  
    public int hashCode() {    
        return Objects.hash(id);  
    } 
}