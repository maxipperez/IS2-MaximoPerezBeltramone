package com.ejercicio4.mvc.models;

import com.ejercicio4.mvc.models.enums.EstadoIntento;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad que registra los intentos de inicio de sesión de los usuarios.
 */
@Entity
public class HistorialInicioSesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoIntento estado;

    /**
     * Usamos LocalDateTime de Java 8+ para guardar fecha y hora precisas del intento.
     */
    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    /**
     * @ManyToOne: Varios registros históricos pertenecen a un mismo Usuario.
     * @JoinColumn: Clave foránea usuario_id que enlaza el intento con quien lo hizo.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public HistorialInicioSesion() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public EstadoIntento getEstado() { return estado; }
    public void setEstado(EstadoIntento estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
