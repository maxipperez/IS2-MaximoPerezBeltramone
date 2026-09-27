package ar.edu.is2.ejercicio5.dto;

import java.time.LocalDate;

public class UsuarioDTO {
  private String id;
  private String nombre;
  private String apellido;
  private String mail;
  private String clave;
  private String claveRepetida;
  private String rol = "VENDEDOR";
  private boolean activo = true;
  private LocalDate alta;

  public String getId() {
    return id;
  }

  public void setId(String v) {
    id = v;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String v) {
    nombre = v;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String v) {
    apellido = v;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String v) {
    mail = v;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String v) {
    clave = v;
  }

  public String getClaveRepetida() {
    return claveRepetida;
  }

  public void setClaveRepetida(String v) {
    claveRepetida = v;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String v) {
    rol = v;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean v) {
    activo = v;
  }

  public LocalDate getAlta() {
    return alta;
  }

  public void setAlta(LocalDate v) {
    alta = v;
  }
}
