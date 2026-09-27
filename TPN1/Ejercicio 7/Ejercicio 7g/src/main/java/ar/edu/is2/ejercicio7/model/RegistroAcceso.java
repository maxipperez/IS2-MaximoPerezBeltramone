package ar.edu.is2.ejercicio7.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class RegistroAcceso {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  private Persona persona;

  @Column(nullable = false)
  private LocalDateTime fechaHoraEntrada;

  private LocalDateTime fechaHoraSalida;

  public Long getId() {
    return id;
  }

  public Persona getPersona() {
    return persona;
  }

  public void setPersona(Persona persona) {
    this.persona = persona;
  }

  public LocalDateTime getFechaHoraEntrada() {
    return fechaHoraEntrada;
  }

  public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
    this.fechaHoraEntrada = fechaHoraEntrada;
  }

  public LocalDateTime getFechaHoraSalida() {
    return fechaHoraSalida;
  }

  public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
    this.fechaHoraSalida = fechaHoraSalida;
  }
}
