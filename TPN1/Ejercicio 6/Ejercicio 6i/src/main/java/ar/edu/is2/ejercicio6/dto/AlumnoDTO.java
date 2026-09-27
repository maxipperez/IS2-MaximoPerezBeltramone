package ar.edu.is2.ejercicio6.dto;

public class AlumnoDTO {
  private Long id;
  private String nombre;
  private String apellido;
  private String dni;
  private Long aulaId;
  private String aula;

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

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
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
}
