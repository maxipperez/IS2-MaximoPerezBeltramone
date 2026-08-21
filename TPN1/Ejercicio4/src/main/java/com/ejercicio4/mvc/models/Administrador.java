package com.ejercicio4.mvc.models;

import jakarta.persistence.*;

/**
 * Entidad que representa a un Administrador.
 * 
 * @Entity: Indica que se creará una tabla para esta clase.
 * @PrimaryKeyJoinColumn: Nombra la clave foránea que relaciona esta tabla con la tabla padre 'usuario'.
 */
@Entity
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Administrador extends Usuario {

    /**
     * @ManyToOne: Relación muchos a uno. Muchos administradores pueden pertenecer a un mismo departamento.
     * @JoinColumn: Crea una columna (departamento_id) en la tabla 'administrador' que es clave foránea 
     *              hacia la tabla 'departamento'.
     */
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    public Administrador() {}

    // Implementación del método abstracto heredado
    @Override
    public boolean validarDatos() {
        return true; 
    }

    // Getters y Setters
    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }
}
