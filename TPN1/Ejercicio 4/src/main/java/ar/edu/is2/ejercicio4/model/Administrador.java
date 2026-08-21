package ar.edu.is2.ejercicio4.model;

import jakarta.persistence.*;

/** Subtipo de Usuario. @ManyToOne: varios administradores pueden pertenecer a un departamento. */
@Entity
public class Administrador extends Usuario {
  @ManyToOne
  @JoinColumn(name = "departamento_id")
  private Departamento departamento;

  public Departamento getDepartamento() {
    return departamento;
  }

  public void setDepartamento(Departamento v) {
    departamento = v;
  }
}
