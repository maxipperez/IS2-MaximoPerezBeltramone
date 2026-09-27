package ar.edu.is2.ejercicio7.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class GrupoFamiliar {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nombreGrupo;

  @OneToOne(optional = false)
  private Socio titular;

  @OneToMany(mappedBy = "grupoFamiliar")
  private List<Familiar> familiares = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public String getNombreGrupo() {
    return nombreGrupo;
  }

  public void setNombreGrupo(String nombreGrupo) {
    this.nombreGrupo = nombreGrupo;
  }

  public Socio getTitular() {
    return titular;
  }

  public void setTitular(Socio titular) {
    this.titular = titular;
  }

  public List<Familiar> getFamiliares() {
    return familiares;
  }
}
