package ar.edu.is2.ejercicio6.model;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Materia {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nombre;

  @ManyToOne(optional = false)
  private Aula aula;

  @ManyToOne(optional = false)
  private Docente docente;

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Aula getAula() {
    return aula;
  }

  public void setAula(Aula aula) {
    this.aula = aula;
  }

  public Docente getDocente() {
    return docente;
  }

  public void setDocente(Docente docente) {
    this.docente = docente;
  }
}
