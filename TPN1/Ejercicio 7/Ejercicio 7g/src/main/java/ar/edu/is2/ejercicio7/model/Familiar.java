package ar.edu.is2.ejercicio7.model;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Familiar extends Persona {
  @Column(nullable = false)
  private String parentesco;

  @ManyToOne
  private GrupoFamiliar grupoFamiliar;

  public String getParentesco() {
    return parentesco;
  }

  public void setParentesco(String parentesco) {
    this.parentesco = parentesco;
  }

  public GrupoFamiliar getGrupoFamiliar() {
    return grupoFamiliar;
  }

  public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
    this.grupoFamiliar = grupoFamiliar;
  }
}
