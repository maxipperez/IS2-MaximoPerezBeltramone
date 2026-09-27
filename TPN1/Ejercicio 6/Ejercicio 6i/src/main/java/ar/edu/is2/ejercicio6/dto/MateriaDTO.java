package ar.edu.is2.ejercicio6.dto;

public class MateriaDTO {
  private Long id;
  private String nombre;
  private Long aulaId;
  private String aula;
  private Long docenteId;
  private String docente;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getAulaId() {
    return aulaId;
  }

  public void setAulaId(Long aulaId) {
    this.aulaId = aulaId;
  }

  public String getAula() {
    return aula;
  }

  public void setAula(String aula) {
    this.aula = aula;
  }

  public Long getDocenteId() {
    return docenteId;
  }

  public void setDocenteId(Long docenteId) {
    this.docenteId = docenteId;
  }

  public String getDocente() {
    return docente;
  }

  public void setDocente(String docente) {
    this.docente = docente;
  }
}
