package ar.edu.is2.ejercicio4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/** Dirección asociada uno a uno a un usuario. @Entity permite que JPA la persista. */
@Entity
public class Direccion {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotBlank private String calle;
  @NotBlank private String numero;
  @NotBlank private String ciudad;
  @NotBlank private String codigoPostal;

  public String getId() {
    return id;
  }

  public String getCalle() {
    return calle;
  }

  public void setCalle(String v) {
    calle = v;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String v) {
    numero = v;
  }

  public String getCiudad() {
    return ciudad;
  }

  public void setCiudad(String v) {
    ciudad = v;
  }

  public String getCodigoPostal() {
    return codigoPostal;
  }

  public void setCodigoPostal(String v) {
    codigoPostal = v;
  }
}
