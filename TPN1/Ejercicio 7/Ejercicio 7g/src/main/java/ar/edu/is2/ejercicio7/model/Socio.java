package ar.edu.is2.ejercicio7.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Socio extends Persona {
  @Column(nullable = false, unique = true)
  private Integer nroSocio;

  private LocalDate fechaAlta = LocalDate.now();

  @OneToOne(mappedBy = "titular", cascade = CascadeType.ALL)
  private GrupoFamiliar grupoFamiliar;

  public Integer getNroSocio() {
    return nroSocio;
  }

  public void setNroSocio(Integer nroSocio) {
    this.nroSocio = nroSocio;
  }

  public LocalDate getFechaAlta() {
    return fechaAlta;
  }

  public GrupoFamiliar getGrupoFamiliar() {
    return grupoFamiliar;
  }

  public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
    this.grupoFamiliar = grupoFamiliar;
  }
}
