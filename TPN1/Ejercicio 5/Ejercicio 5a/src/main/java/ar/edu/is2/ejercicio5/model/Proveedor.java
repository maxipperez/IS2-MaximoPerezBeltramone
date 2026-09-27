package ar.edu.is2.ejercicio5.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Proveedor {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotBlank
  @Column(nullable = false, unique = true)
  private String cuit;

  @NotBlank
  @Column(nullable = false)
  private String razonSocial;

  private String telefono;
  private boolean activo = true;

  public String getId() {
    return id;
  }

  public String getCuit() {
    return cuit;
  }

  public void setCuit(String v) {
    cuit = v;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(String v) {
    razonSocial = v;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String v) {
    telefono = v;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean v) {
    activo = v;
  }
}
