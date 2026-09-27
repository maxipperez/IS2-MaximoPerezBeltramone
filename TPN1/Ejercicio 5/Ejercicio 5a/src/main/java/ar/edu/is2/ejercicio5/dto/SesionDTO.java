package ar.edu.is2.ejercicio5.dto;

import java.io.Serializable;

public class SesionDTO implements Serializable {
  private final String id;
  private final String nombreCompleto;
  private final String mail;
  private final String rol;

  public SesionDTO(String id, String nombreCompleto, String mail, String rol) {
    this.id = id;
    this.nombreCompleto = nombreCompleto;
    this.mail = mail;
    this.rol = rol;
  }

  public boolean isAdministrador() {
    return "ADMINISTRADOR".equals(rol);
  }

  public String getId() {
    return id;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public String getMail() {
    return mail;
  }

  public String getRol() {
    return rol;
  }
}
