package com.ejercicio4.mvc.models;

import jakarta.persistence.*;
import java.util.List;

/**
 * Entidad que representa un Departamento en la empresa.
 */
@Entity
public class Departamento {

    /**
     * @Id y @GeneratedValue(IDENTITY): Utilizamos un ID auto numérico (1, 2, 3...) 
     * para tablas de catálogo sencillas.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String ubicacion;

    /**
     * @OneToMany: Un departamento puede tener muchos administradores.
     * Relación bidireccional mapeada por el atributo 'departamento' en Administrador.
     */
    @OneToMany(mappedBy = "departamento")
    private List<Administrador> administradores;

    /**
     * Eliminado lógico: flag para no borrar físicamente el registro,
     * permitiendo mantener el historial referencial.
     */
    @Column(nullable = false)
    private boolean eliminado = false;

    public Departamento() {}

    public void asignarAdministrador(Administrador a) {
        this.administradores.add(a);
        a.setDepartamento(this);
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public List<Administrador> getAdministradores() { return administradores; }
    public void setAdministradores(List<Administrador> administradores) { this.administradores = administradores; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
}
