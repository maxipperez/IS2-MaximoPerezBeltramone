package ar.edu.is2.ejercicio4.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Auditoría: muchos intentos pertenecen a un único usuario. */
@Entity
public class HistorialInicioSesion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private EstadoIntento estado;

  private LocalDateTime fecha = LocalDateTime.now();

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  public Long getId() {
    return id;
  }

  public EstadoIntento getEstado() {
    return estado;
  }

  public void setEstado(EstadoIntento v) {
    estado = v;
  }

  public LocalDateTime getFecha() {
    return fecha;
  }

  public void setFecha(LocalDateTime v) {
    fecha = v;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario v) {
    usuario = v;
  }
}
