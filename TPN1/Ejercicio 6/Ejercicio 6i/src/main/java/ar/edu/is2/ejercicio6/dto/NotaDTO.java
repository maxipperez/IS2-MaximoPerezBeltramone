package ar.edu.is2.ejercicio6.dto;

import java.time.LocalDate;

public class NotaDTO {
  private Long id;
  private Double valor;
  private LocalDate fecha;
  private Long alumnoId;
  private String alumno;
  private Long materiaId;
  private String materia;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getAlumnoId() {
    return alumnoId;
  }

  public void setAlumnoId(Long alumnoId) {
    this.alumnoId = alumnoId;
  }

  public String getAlumno() {
    return alumno;
  }

  public void setAlumno(String alumno) {
    this.alumno = alumno;
  }

  public Long getMateriaId() {
    return materiaId;
  }

  public void setMateriaId(Long materiaId) {
    this.materiaId = materiaId;
  }

  public String getMateria() {
    return materia;
  }

  public void setMateria(String materia) {
    this.materia = materia;
  }
}
