package ar.edu.is2.ejercicio7.dto;

import java.time.LocalDate;

public class SocioDTO {
  private Long id;
  private String nombre;
  private String apellido;
  private String dni;
  private Integer nroSocio;
  private LocalDate fechaAlta;
  private Long grupoId;
  private String grupo;

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

  public Integer getNroSocio() {
    return nroSocio;
  }

  public void setNroSocio(Integer nroSocio) {
    this.nroSocio = nroSocio;
  }

  public LocalDate getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(LocalDate fechaAlta) {
    this.fechaAlta = fechaAlta;
  }

  public Long getGrupoId() {
    return grupoId;
  }

  public void setGrupoId(Long grupoId) {
    this.grupoId = grupoId;
  }

  public String getGrupo() {
    return grupo;
  }

  public void setGrupo(String grupo) {
    this.grupo = grupo;
  }
}
