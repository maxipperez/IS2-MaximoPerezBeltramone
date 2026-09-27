package ar.edu.is2.ejercicio6.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Nota {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Double valor;

  @Column(nullable = false)
  private LocalDate fecha;

  @ManyToOne(optional = false)
  private Alumno alumno;

  @ManyToOne(optional = false)
  private Materia materia;

  public Long getId() {
    return id;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public Alumno getAlumno() {
    return alumno;
  }

  public void setAlumno(Alumno alumno) {
    this.alumno = alumno;
  }

  public Materia getMateria() {
    return materia;
  }

  public void setMateria(Materia materia) {
    this.materia = materia;
  }
}
